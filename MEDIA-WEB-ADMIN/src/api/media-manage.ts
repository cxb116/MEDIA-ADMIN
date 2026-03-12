import request from '@/utils/http'

// ==================== 应用管理 API ====================

/**
 * 分页查询应用列表
 */
export function fetchGetAppList(params: Api.MediaManage.AppSearchParams) {
  return request.get<Api.MediaManage.AppList>({
    url: '/api/media/app/list',
    params
  })
}

/**
 * 根据ID查询应用
 */
export function fetchGetAppById(id: number) {
  return request.get<Api.MediaManage.AppListItem>({
    url: `/api/media/app/${id}`
  })
}

/**
 * 创建应用
 */
export function fetchCreateApp(data: Api.MediaManage.AppCreateParams) {
  return request.post<Api.MediaManage.AppListItem>({
    url: '/api/media/app/create',
    data
  })
}

/**
 * 更新应用
 */
export function fetchUpdateApp(data: Api.MediaManage.AppUpdateParams) {
  return request.post<Api.MediaManage.AppListItem>({
    url: '/api/media/app/update',
    data
  })
}

/**
 * 删除应用
 */
export function fetchDeleteApp(id: number) {
  return request.post<void>({
    url: `/api/media/app/delete/${id}`
  })
}

/**
 * 批量删除应用
 */
export function fetchBatchDeleteApp(ids: number[]) {
  return request.post<void>({
    url: '/api/media/app/batchDelete',
    data: ids
  })
}

/**
 * 根据媒体ID查询应用列表
 */
export function fetchGetAppListByMediaId(mediaId: number) {
  return request.get<Api.MediaManage.AppListItem[]>({
    url: `/api/media/app/list/${mediaId}`
  })
}

// ==================== 广告位管理 API ====================

/**
 * 分页查询广告位列表
 */
export function fetchGetSlotList(params: Api.MediaManage.SlotSearchParams) {
  return request.get<Api.MediaManage.SlotList>({
    url: '/api/media/slot/list',
    params
  })
}

/**
 * 根据ID查询广告位
 */
export function fetchGetSlotById(id: number) {
  return request.get<Api.MediaManage.SlotListItem>({
    url: `/api/media/slot/${id}`
  })
}

/**
 * 创建广告位
 */
export function fetchCreateSlot(data: Api.MediaManage.SlotCreateParams) {
  return request.post<Api.MediaManage.SlotListItem>({
    url: '/api/media/slot/create',
    data
  })
}

/**
 * 更新广告位
 */
export function fetchUpdateSlot(data: Api.MediaManage.SlotUpdateParams) {
  return request.post<Api.MediaManage.SlotListItem>({
    url: '/api/media/slot/update',
    data
  })
}

/**
 * 删除广告位
 */
export function fetchDeleteSlot(id: number) {
  return request.post<void>({
    url: `/api/media/slot/delete/${id}`
  })
}

/**
 * 批量删除广告位
 */
export function fetchBatchDeleteSlot(ids: number[]) {
  return request.post<void>({
    url: '/api/media/slot/batchDelete',
    data: ids
  })
}

/**
 * 根据应用ID查询广告位列表
 */
export function fetchGetSlotListByAppId(appId: number) {
  return request.get<Api.MediaManage.SlotListItem[]>({
    url: `/api/media/slot/list/app/${appId}`
  })
}

/**
 * 根据媒体ID查询广告位列表
 */
export function fetchGetSlotListByMediaId(mediaId: number) {
  return request.get<Api.MediaManage.SlotListItem[]>({
    url: `/api/media/slot/list/media/${mediaId}`
  })
}

// ==================== 数据查询 API ====================

/**
 * 分页查询数据列表
 */
export function fetchGetDataList(params: Api.MediaManage.DataSearchParams) {
  return request.get<Api.MediaManage.DataList>({
    url: '/api/media/data/list',
    params
  })
}

/**
 * 根据ID查询数据
 */
export function fetchGetDataById(id: number) {
  return request.get<Api.MediaManage.DataListItem>({
    url: `/api/media/data/${id}`
  })
}

/**
 * 按月份查询数据
 */
export function fetchGetDataByMonth(month: string) {
  return request.get<{ month: string; records: Api.MediaManage.DataListItem[]; total: number }>({
    url: `/api/media/data/month/${month}`
  })
}

/**
 * 按月份查询日表数据
 */
export function fetchGetDayDataByMonth(month: string, params?: { mediaId?: number; appId?: number; sspSlotId?: number }) {
  return request.get<{ month: string; tableType: string; records: Api.MediaManage.DataListItem[]; total: number }>({
    url: `/api/media/data/day/${month}`,
    params
  })
}

/**
 * 按月份查询小时表数据
 */
export function fetchGetHourDataByMonth(month: string, params?: { mediaId?: number; appId?: number; sspSlotId?: number }) {
  return request.get<{ month: string; tableType: string; records: Api.MediaManage.DataListItem[]; total: number }>({
    url: `/api/media/data/hour/${month}`,
    params
  })
}

/**
 * 根据广告位ID查询数据列表
 */
export function fetchGetDataListBySlotId(sspSlotId: number) {
  return request.get<Api.MediaManage.DataListItem[]>({
    url: `/api/media/data/list/slot/${sspSlotId}`
  })
}

/**
 * 根据应用ID查询数据列表
 */
export function fetchGetDataListByAppId(appId: number) {
  return request.get<Api.MediaManage.DataListItem[]>({
    url: `/api/media/data/list/app/${appId}`
  })
}

/**
 * 根据媒体ID查询数据列表
 */
export function fetchGetDataListByMediaId(mediaId: number) {
  return request.get<Api.MediaManage.DataListItem[]>({
    url: `/api/media/data/list/media/${mediaId}`
  })
}

// ==================== DSP广告配置 API ====================

/**
 * 获取所有广告类型
 */
export function fetchGetAdTypes() {
  return request.get<Api.DspConfig.AdTypeResponse[]>({
    url: '/api/dsp/config/ad-types'
  })
}

/**
 * 根据广告类型ID获取场景列表
 */
export function fetchGetAdScenes(typeId: number) {
  return request.get<Api.DspConfig.AdSceneResponse[]>({
    url: `/api/dsp/config/ad-scenes/${typeId}`
  })
}

/**
 * 根据广告类型ID获取尺寸列表
 */
export function fetchGetAdSizes(typeId: number) {
  return request.get<Api.DspConfig.AdSizeResponse[]>({
    url: `/api/dsp/config/ad-sizes/${typeId}`
  })
}
