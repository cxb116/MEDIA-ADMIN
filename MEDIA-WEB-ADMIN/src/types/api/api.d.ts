/**
 * API 接口类型定义模块
 *
 * 提供所有后端接口的类型定义
 *
 * ## 主要功能
 *
 * - 通用类型（分页参数、响应结构等）
 * - 认证类型（登录、用户信息等）
 * - 系统管理类型（用户、角色等）
 * - 全局命名空间声明
 *
 * ## 使用场景
 *
 * - API 请求参数类型约束
 * - API 响应数据类型定义
 * - 接口文档类型同步
 *
 * ## 注意事项
 *
 * - 在 .vue 文件使用需要在 eslint.config.mjs 中配置 globals: { Api: 'readonly' }
 * - 使用全局命名空间，无需导入即可使用
 *
 * ## 使用方式
 *
 * ```typescript
 * const params: Api.Auth.LoginParams = { userName: 'admin', password: '123456' }
 * const response: Api.Auth.UserInfo = await fetchUserInfo()
 * ```
 *
 * @module types/api/api
 * @author Art Design Pro Team
 */

declare namespace Api {
  /** 通用类型 */
  namespace Common {
    /** 分页参数 */
    interface PaginationParams {
      /** 当前页码 */
      current: number
      /** 每页条数 */
      size: number
      /** 总条数 */
      total: number
    }

    /** 通用搜索参数 */
    type CommonSearchParams = Pick<PaginationParams, 'current' | 'size'>

    /** 分页响应基础结构 */
    interface PaginatedResponse<T = any> {
      records: T[]
      current: number
      size: number
      total: number
    }

    /** 启用状态 */
    type EnableStatus = '1' | '2'
  }

  /** 认证类型 */
  namespace Auth {
    /** 登录参数 */
    interface LoginParams {
      userName: string
      password: string
    }

    /** 登录响应 */
    interface LoginResponse {
      token: string
      refreshToken: string
    }

    /** 用户信息 */
    interface UserInfo {
      buttons: string[]
      roles: string[]
      userId: number
      userName: string
      email: string
      avatar?: string
    }
  }

  /** 系统管理类型 */
  namespace SystemManage {
    /** 用户列表 */
    type UserList = Api.Common.PaginatedResponse<UserListItem>

    /** 用户列表项 */
    interface UserListItem {
      id: number
      avatar: string
      status: string
      userName: string
      userGender: string
      nickName: string
      userPhone: string
      userEmail: string
      userRoles: string[]
      createBy: string
      createTime: string
      updateBy: string
      updateTime: string
    }

    /** 用户搜索参数 */
    type UserSearchParams = Partial<
      Pick<UserListItem, 'id' | 'userName' | 'userGender' | 'userPhone' | 'userEmail' | 'status'> &
        Api.Common.CommonSearchParams
    >

    /** 角色列表 */
    type RoleList = Api.Common.PaginatedResponse<RoleListItem>

    /** 角色列表项 */
    interface RoleListItem {
      roleId: number
      roleName: string
      roleCode: string
      description: string
      enabled: boolean
      createTime: string
    }

    /** 角色搜索参数 */
    type RoleSearchParams = Partial<
      Pick<RoleListItem, 'roleId' | 'roleName' | 'roleCode' | 'description' | 'enabled'> &
        Api.Common.CommonSearchParams
    >
  }

  /** 媒体管理类型 */
  namespace MediaManage {
    // ==================== 应用管理 ====================

    /** 应用列表 */
    type AppList = Api.Common.PaginatedResponse<AppListItem>

    /** 应用列表项 */
    interface AppListItem {
      id: number
      mediaId: number
      name: string
      osType: number
      osTypeName: string
      accessType: number
      accessTypeName: string
      pkg: string
      downloadUrl: string
      enable: number
      enableName: string
      createTime: string
      updateTime: string
      remark: string
    }

    /** 应用搜索参数 */
    type AppSearchParams = Partial<
      Pick<AppListItem, 'mediaId' | 'name' | 'osType' | 'enable'> &
        Api.Common.CommonSearchParams
    >

    /** 应用创建参数 */
    interface AppCreateParams {
      mediaId: number
      name: string
      osType: number
      accessType?: number
      pkg?: string
      downloadUrl?: string
      remark?: string
    }

    /** 应用更新参数 */
    interface AppUpdateParams {
      id: number
      name?: string
      osType?: number
      accessType?: number
      pkg?: string
      downloadUrl?: string
      enable?: number
      remark?: string
    }

    // ==================== 广告位管理 ====================

    /** 广告位列表 */
    type SlotList = Api.Common.PaginatedResponse<SlotListItem>

    /** 广告位列表项 */
    interface SlotListItem {
      id: number
      mediaId: number
      appId: number
      name: string
      nameAlise: string
      adSceneId: number
      adTypeId: number
      adSizeId: number
      sspPayType: number
      sspPayTypeName: string
      sspDealRatio: number
      width: number
      height: number
      adImage: string
      interactionType: number
      interactionTypeName: string
      enable: number
      enableName: string
      createTime: string
      updateTime: string
      remark: string
    }

    /** 广告位搜索参数 */
    type SlotSearchParams = Partial<
      Pick<SlotListItem, 'mediaId' | 'appId' | 'name' | 'enable'> &
        Api.Common.CommonSearchParams
    >

    /** 广告位创建参数 */
    interface SlotCreateParams {
      mediaId: number
      appId: number
      name: string
      nameAlise?: string
      adSceneId?: number
      adTypeId?: number
      adSizeId?: number
      sspPayType?: number
      sspDealRatio?: number
      width?: number
      height?: number
      adImage?: string
      interactionType?: number
      remark?: string
    }

    /** 广告位更新参数 */
    interface SlotUpdateParams {
      id: number
      name?: string
      nameAlise?: string
      adSceneId?: number
      adTypeId?: number
      adSizeId?: number
      sspPayType?: number
      sspDealRatio?: number
      width?: number
      height?: number
      adImage?: string
      interactionType?: number
      enable?: number
      remark?: string
    }

    // ==================== 数据查询 ====================

    /** 数据列表 */
    type DataList = Api.Common.PaginatedResponse<DataListItem>

    /** 数据列表项 */
    interface DataListItem {
      id: number
      mediaId: number
      appId: number
      sspSlotId: number
      dspSlotId: number
      dspSlotCode: string
      showPv: number
      showUv: number
      clickPv: number
      clickUv: number
      reqPv: number
      reqUv: number
      discard: number
      retPv: number
      retUv: number
      spend: number
      income: number
      discountClickPv: number
      discountShowPv: number
      dplsuccPv: number
      completePv: number
      installPv: number
      activatePv: number
      date: number
      dateStr: string
    }

    /** 数据搜索参数 */
    interface DataSearchParams extends Api.Common.CommonSearchParams {
      mediaId?: number
      appId?: number
      sspSlotId?: number
      startDate?: number
      endDate?: number
      month?: string
    }
  }
}
