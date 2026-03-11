<template>
  <div class="data-page art-full-height">
    <ElCard class="mb-4" shadow="never">
      <ElForm :inline="true" :model="searchForm">
        <ElFormItem label="日期范围">
          <ElDatePicker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYYMMDD"
            @change="handleDateChange"
          />
        </ElFormItem>
        <ElFormItem label="广告位">
          <ElSelect v-model="searchForm.sspSlotId" placeholder="请选择广告位" clearable>
            <ElOption
              v-for="slot in slotOptions"
              :key="slot.id"
              :label="slot.name"
              :value="slot.id"
            />
          </ElSelect>
        </ElFormItem>
        <ElFormItem>
          <ElButton type="primary" @click="handleSearch">查询</ElButton>
          <ElButton @click="handleReset">重置</ElButton>
        </ElFormItem>
      </ElForm>
    </ElCard>

    <ElCard class="art-table-card" shadow="never">
      <ArtTableHeader v-model:columns="columnChecks" :loading="loading" @refresh="refreshData">
        <template #left>
          <ElSpace wrap>
            <ElButton @click="exportData" v-ripple>导出数据</ElButton>
          </ElSpace>
        </template>
      </ArtTableHeader>

      <ArtTable
        :loading="loading"
        :data="data"
        :columns="columns"
        :pagination="pagination"
        @pagination:size-change="handleSizeChange"
        @pagination:current-change="handleCurrentChange"
      />
    </ElCard>
  </div>
</template>

<script setup lang="ts">
  import { useTable } from '@/hooks/core/useTable'
  import { fetchGetDataList, fetchGetSlotListByMediaId } from '@/api/media-manage'
  import { ElTag } from 'element-plus'

  defineOptions({ name: 'MediaData' })

  type DataListItem = Api.MediaManage.DataListItem

  const dateRange = ref<[string, string] | null>(null)
  const slotOptions = ref<{ id: number; name: string }[]>([])

  const searchForm = ref({
    mediaId: undefined as number | undefined,
    appId: undefined as number | undefined,
    sspSlotId: undefined as number | undefined,
    startDate: undefined as number | undefined,
    endDate: undefined as number | undefined
  })

  const formatNumber = (num: number) => {
    if (!num) return '0'
    return num.toLocaleString()
  }

  const formatMoney = (num: number) => {
    if (!num) return '¥0.00'
    return `¥${(num / 100).toFixed(2)}`
  }

  const {
    columns,
    columnChecks,
    data,
    loading,
    pagination,
    getData,
    searchParams,
    handleSizeChange,
    handleCurrentChange,
    refreshData
  } = useTable({
    core: {
      apiFn: fetchGetDataList,
      apiParams: {
        current: 1,
        size: 20,
        ...searchForm.value
      },
      columnsFactory: () => [
        { type: 'index', width: 60, label: '序号' },
        { prop: 'dateStr', label: '日期', width: 120 },
        { prop: 'dspSlotCode', label: 'DSP广告位', minWidth: 150 },
        {
          prop: 'reqPv',
          label: '请求数',
          width: 100,
          formatter: (row) => formatNumber(row.reqPv)
        },
        {
          prop: 'showPv',
          label: '展示数',
          width: 100,
          formatter: (row) => formatNumber(row.showPv)
        },
        {
          prop: 'clickPv',
          label: '点击数',
          width: 100,
          formatter: (row) => formatNumber(row.clickPv)
        },
        {
          prop: 'clickRate',
          label: '点击率',
          width: 100,
          formatter: (row) => row.showPv ? `${((row.clickPv / row.showPv) * 100).toFixed(2)}%` : '-'
        },
        {
          prop: 'income',
          label: '收入',
          width: 120,
          formatter: (row) => formatMoney(row.income)
        },
        {
          prop: 'spend',
          label: '支出',
          width: 120,
          formatter: (row) => formatMoney(row.spend)
        },
        {
          prop: 'completePv',
          label: '完成数',
          width: 100,
          formatter: (row) => formatNumber(row.completePv)
        },
        {
          prop: 'installPv',
          label: '安装数',
          width: 100,
          formatter: (row) => formatNumber(row.installPv)
        }
      ]
    }
  })

  const loadSlotOptions = async () => {
    try {
      const res = await fetchGetSlotListByMediaId(1)
      slotOptions.value = res || []
    } catch (e) {
      console.error('加载广告位选项失败', e)
    }
  }

  const handleDateChange = (val: [string, string] | null) => {
    if (val) {
      searchForm.value.startDate = Number(val[0])
      searchForm.value.endDate = Number(val[1])
    } else {
      searchForm.value.startDate = undefined
      searchForm.value.endDate = undefined
    }
  }

  const handleSearch = () => {
    Object.assign(searchParams, searchForm.value)
    getData()
  }

  const handleReset = () => {
    dateRange.value = null
    searchForm.value = {
      mediaId: undefined,
      appId: undefined,
      sspSlotId: undefined,
      startDate: undefined,
      endDate: undefined
    }
    Object.assign(searchParams, searchForm.value)
    getData()
  }

  const exportData = () => {
    ElMessage.info('导出功能开发中...')
  }

  onMounted(() => {
    loadSlotOptions()
  })
</script>
