<template>
  <div class="slot-page art-full-height">
    <SlotSearch v-model="searchForm" @search="handleSearch" @reset="resetSearchParams"></SlotSearch>

    <ElCard class="art-table-card" shadow="never">
      <ArtTableHeader v-model:columns="columnChecks" :loading="loading" @refresh="refreshData">
        <template #left>
          <ElSpace wrap>
            <ElButton @click="showDialog('add')" v-ripple>新增广告位</ElButton>
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

      <SlotDialog
        v-model:visible="dialogVisible"
        :type="dialogType"
        :slot-data="currentSlotData"
        @submit="handleDialogSubmit"
      />
    </ElCard>
  </div>
</template>

<script setup lang="ts">
  import ArtButtonTable from '@/components/core/forms/art-button-table/index.vue'
  import { useTable } from '@/hooks/core/useTable'
  import { fetchGetSlotList, fetchDeleteSlot } from '@/api/media-manage'
  import SlotSearch from './modules/slot-search.vue'
  import SlotDialog from './modules/slot-dialog.vue'
  import { ElTag, ElMessageBox } from 'element-plus'
  import { DialogType } from '@/types'

  defineOptions({ name: 'MediaSlot' })

  type SlotListItem = Api.MediaManage.SlotListItem

  const dialogType = ref<DialogType>('add')
  const dialogVisible = ref(false)
  const currentSlotData = ref<Partial<SlotListItem>>({})
  const selectedRows = ref<SlotListItem[]>([])

  const searchForm = ref({
    mediaId: undefined as number | undefined,
    appId: undefined as number | undefined,
    name: undefined,
    enable: undefined as number | undefined
  })

  const ENABLE_CONFIG = {
    0: { type: 'danger' as const, text: '禁用' },
    1: { type: 'success' as const, text: '正常' },
    2: { type: 'warning' as const, text: '审核中' },
    3: { type: 'danger' as const, text: '拒绝' }
  } as const

  const getEnableConfig = (enable: number) =>
    ENABLE_CONFIG[enable as keyof typeof ENABLE_CONFIG] || { type: 'info' as const, text: '未知' }

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
      apiFn: fetchGetSlotList,
      apiParams: {
        current: 1,
        size: 20,
        ...searchForm.value
      },
      columnsFactory: () => [
        { type: 'selection' },
        { type: 'index', width: 60, label: '序号' },
        { prop: 'name', label: '广告位名称', minWidth: 150 },
        { prop: 'nameAlise', label: '别名', minWidth: 120 },
        {
          prop: 'sspPayType',
          label: '结算方式',
          width: 100,
          formatter: (row) => row.sspPayTypeName || '-'
        },
        {
          prop: 'sspDealRatio',
          label: '分成比例',
          width: 100,
          formatter: (row) => (row.sspDealRatio ? `${(row.sspDealRatio * 100).toFixed(1)}%` : '-')
        },
        { prop: 'width', label: '宽度', width: 80 },
        { prop: 'height', label: '高度', width: 80 },
        {
          prop: 'interactionType',
          label: '交互类型',
          minWidth: 150,
          formatter: (row) => row.interactionTypeName || '-'
        },
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
              h(ArtButtonTable, { type: 'delete', onClick: () => deleteSlot(row) })
            ])
        }
      ]
    }
  })

  const handleSearch = (params: Record<string, any>) => {
    Object.assign(searchParams, params)
    getData()
  }

  const showDialog = (type: DialogType, row?: SlotListItem): void => {
    dialogType.value = type
    currentSlotData.value = row || {}
    nextTick(() => {
      dialogVisible.value = true
    })
  }

  const deleteSlot = (row: SlotListItem): void => {
    ElMessageBox.confirm(`确定要删除广告位「${row.name}」吗？`, '删除广告位', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      await fetchDeleteSlot(row.id)
      ElMessage.success('删除成功')
      refreshData()
    })
  }

  const handleDialogSubmit = async () => {
    dialogVisible.value = false
    currentSlotData.value = {}
    refreshData()
  }

  const handleSelectionChange = (selection: SlotListItem[]): void => {
    selectedRows.value = selection
  }
</script>
