<template>
  <div class="slot-page art-full-height">
    <SlotSearch v-model="searchForm" @search="handleSearch" @reset="customResetSearchParams"></SlotSearch>

    <ElCard class="art-table-card" shadow="never">
      <ArtTableHeader v-model:columns="columnChecks" :loading="loading" @refresh="customRefreshData">
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
  import { ElTag, ElMessageBox, ElCard, ElSpace, ElButton, ElMessage } from 'element-plus'
  import { DialogType } from '@/types'

  defineOptions({ name: 'MediaSlot' })

  type SlotListItem = Api.MediaManage.SlotListItem

  const dialogType = ref<DialogType>('add')
  const dialogVisible = ref(false)
  const currentSlotData = ref<Partial<SlotListItem>>({})
  const selectedRows = ref<SlotListItem[]>([])

  // 获取当前登录的媒体用户信息
  const getCurrentMediaId = (): number | undefined => {
    try {
      const mediaUserInfo = localStorage.getItem('media_user_info')
      if (mediaUserInfo) {
        const userInfo = JSON.parse(mediaUserInfo)
        return userInfo.id
      }
    } catch (error) {
      console.error('Failed to get media user info:', error)
    }
    return undefined
  }

  const searchForm = ref({
    mediaId: getCurrentMediaId(),
    appId: undefined as number | undefined,
    name: undefined,
    enable: undefined as number | undefined
  })

  const ENABLE_CONFIG = {
    1: { type: 'success' as const, text: '启用' },
    2: { type: 'warning' as const, text: '审核中' },
    3: { type: 'danger' as const, text: '禁用' }
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
        ...searchForm.value,
        mediaId: getCurrentMediaId()
      },
      columnsFactory: () => [
        { type: 'selection' },
        { type: 'index', width: 60, label: '序号' },
        { prop: 'name', label: '广告位名称', minWidth: 150 },
        {
          prop: 'appName',
          label: '应用名称',
          width: 120,
          formatter: (row) => row.appName || '-'
        },
        {
          prop: 'appOsTypeName',
          label: '系统',
          width: 80,
          formatter: (row) => {
            const osType = row.appOsType
            if (osType === 1) {
              return h(ElTag, { type: 'success', size: 'small' }, () => 'Android')
            } else if (osType === 2) {
              return h(ElTag, { type: 'primary', size: 'small' }, () => 'iOS')
            }
            return row.appOsTypeName || '-'
          }
        },
        {
          prop: 'adTypeName',
          label: '广告类型',
          width: 100,
          formatter: (row) => row.adTypeName || '-'
        },
        {
          prop: 'adSceneName',
          label: '广告场景',
          width: 120,
          formatter: (row) => row.adSceneName || '-'
        },
        {
          prop: 'adSizeName',
          label: '广告尺寸',
          width: 120,
          formatter: (row) => row.adSizeName || '-'
        },
        {
          prop: 'width',
          label: '尺寸',
          width: 120,
          formatter: (row) => {
            const w = row.width || 0
            const h = row.height || 0
            return w || h ? `${w} × ${h}` : '-'
          }
        },
        {
          prop: 'adImage',
          label: '示例图片',
          width: 150,
          formatter: (row) => row.adImage || '-'
        },
        {
          prop: 'interactionType',
          label: '交互类型',
          minWidth: 150,
          formatter: (row) => {
            if (!row.interactionType) return '-'
            const types = []
            if (row.interactionType & 1) types.push('打开网页')
            if (row.interactionType & 2) types.push('Deeplink')
            if (row.interactionType & 4) types.push('直接下载')
            if (row.interactionType & 8) types.push('广点通')
            if (row.interactionType & 16) types.push('小程序')
            if (row.interactionType & 32) types.push('应用商店')
            if (row.interactionType & 64) types.push('快应用')
            return types.length > 0 ? types.join('、') : '-'
          }
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
    const mediaId = getCurrentMediaId()
    Object.assign(searchParams, { mediaId, ...params })
    getData()
  }

  // 重写 refreshData，确保带上 mediaId
  const customRefreshData = async () => {
    const mediaId = getCurrentMediaId()
    Object.assign(searchParams, { mediaId })
    await refreshData()
  }

  // 重写 resetSearchParams，确保带上 mediaId
  const customResetSearchParams = async () => {
    await resetSearchParams()
    const mediaId = getCurrentMediaId()
    Object.assign(searchParams, { mediaId })
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
      customRefreshData()
    })
  }

  const handleDialogSubmit = async () => {
    dialogVisible.value = false
    currentSlotData.value = {}
    customRefreshData()
  }

  const handleSelectionChange = (selection: SlotListItem[]): void => {
    selectedRows.value = selection
  }
</script>
