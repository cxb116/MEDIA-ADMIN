<template>
  <div class="app-page art-full-height">
    <AppSearch v-model="searchForm" @search="handleSearch" @reset="resetSearchParams"></AppSearch>

    <ElCard class="art-table-card" shadow="never">
      <ArtTableHeader v-model:columns="columnChecks" :loading="loading" @refresh="refreshData">
        <template #left>
          <ElSpace wrap>
            <ElButton @click="showDialog('add')" v-ripple>新增应用</ElButton>
          </ElSpace>
        </template>
      </ArtTableHeader>

      <ArtTable
        :loading="loading"
        :data="data"
        :columns="columns"
        :pagination="pagination"
        @selection-change="handleSelectionChange"
        @pagination:size-change="handleSizeChange"
        @pagination:current-change="handleCurrentChange"
      />

      <AppDialog
        v-model:visible="dialogVisible"
        :type="dialogType"
        :app-data="currentAppData"
        @submit="handleDialogSubmit"
      />
    </ElCard>
  </div>
</template>

<script setup lang="ts">
  import ArtButtonTable from '@/components/core/forms/art-button-table/index.vue'
  import { useTable } from '@/hooks/core/useTable'
  import {
    fetchGetAppList,
    fetchCreateApp,
    fetchUpdateApp,
    fetchDeleteApp,
    fetchBatchDeleteApp
  } from '@/api/media-manage'
  import AppSearch from './modules/app-search.vue'
  import AppDialog from './modules/app-dialog.vue'
  import { ElTag, ElMessageBox } from 'element-plus'
  import { DialogType } from '@/types'

  defineOptions({ name: 'MediaApp' })

  type AppListItem = Api.MediaManage.AppListItem

  const dialogType = ref<DialogType>('add')
  const dialogVisible = ref(false)
  const currentAppData = ref<Partial<AppListItem>>({})
  const selectedRows = ref<AppListItem[]>([])

  const searchForm = ref({
    mediaId: undefined as number | undefined,
    name: undefined,
    osType: undefined as number | undefined,
    enable: undefined as number | undefined
  })

  const OS_TYPE_CONFIG = {
    1: { type: 'success' as const, text: 'Android' },
    2: { type: 'primary' as const, text: 'iOS' }
  } as const

  const ENABLE_CONFIG = {
    0: { type: 'danger' as const, text: '禁用' },
    1: { type: 'success' as const, text: '正常' },
    2: { type: 'warning' as const, text: '审核中' },
    3: { type: 'danger' as const, text: '拒绝' }
  } as const

  const getOsTypeConfig = (osType: number) => OS_TYPE_CONFIG[osType as keyof typeof OS_TYPE_CONFIG] || { type: 'info' as const, text: '未知' }
  const getEnableConfig = (enable: number) => ENABLE_CONFIG[enable as keyof typeof ENABLE_CONFIG] || { type: 'info' as const, text: '未知' }

  const {
    columns,
    columnChecks,
    data,
    loading,
    pagination,
    getData,
    searchParams,
    resetSearchParams,
    handleSizeChange,
    handleCurrentChange,
    refreshData
  } = useTable({
    core: {
      apiFn: fetchGetAppList,
      apiParams: {
        current: 1,
        size: 20,
        ...searchForm.value
      },
      columnsFactory: () => [
        { type: 'selection' },
        { type: 'index', width: 60, label: '序号' },
        { prop: 'name', label: '应用名称', minWidth: 150 },
        {
          prop: 'osType',
          label: '操作系统',
          width: 100,
          formatter: (row) => {
            const config = getOsTypeConfig(row.osType)
            return h(ElTag, { type: config.type }, () => config.text)
          }
        },
        {
          prop: 'accessType',
          label: '接入方式',
          width: 100,
          formatter: (row) => row.accessTypeName || '-'
        },
        { prop: 'pkg', label: '包名', minWidth: 180 },
        {
          prop: 'enable',
          label: '状态',
          width: 100,
          formatter: (row) => {
            const config = getEnableConfig(row.enable)
            return h(ElTag, { type: config.type }, () => config.text)
          }
        },
        { prop: 'createTime', label: '创建时间', width: 180 },
        {
          prop: 'operation',
          label: '操作',
          width: 120,
          fixed: 'right',
          formatter: (row) =>
            h('div', [
              h(ArtButtonTable, { type: 'edit', onClick: () => showDialog('edit', row) }),
              h(ArtButtonTable, { type: 'delete', onClick: () => deleteApp(row) })
            ])
        }
      ]
    }
  })

  const handleSearch = (params: Record<string, any>) => {
    Object.assign(searchParams, params)
    getData()
  }

  const showDialog = (type: DialogType, row?: AppListItem): void => {
    dialogType.value = type
    currentAppData.value = row || {}
    nextTick(() => {
      dialogVisible.value = true
    })
  }

  const deleteApp = (row: AppListItem): void => {
    ElMessageBox.confirm(`确定要删除应用「${row.name}」吗？`, '删除应用', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      await fetchDeleteApp(row.id)
      ElMessage.success('删除成功')
      refreshData()
    })
  }

  const handleDialogSubmit = async () => {
    dialogVisible.value = false
    currentAppData.value = {}
    refreshData()
  }

  const handleSelectionChange = (selection: AppListItem[]): void => {
    selectedRows.value = selection
  }
</script>
