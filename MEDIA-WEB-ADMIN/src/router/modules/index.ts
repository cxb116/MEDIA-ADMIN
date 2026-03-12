import { AppRouteRecord } from '@/types/router'
import { dashboardRoutes } from './dashboard'
import { userCenterRoute } from './system'
import { mediaAppRoutes, mediaSlotRoutes, mediaDataRoutes } from './media'

/**
 * 导出所有模块化路由
 */
export const routeModules: AppRouteRecord[] = [
  dashboardRoutes,
  mediaAppRoutes,
  mediaSlotRoutes,
  mediaDataRoutes,
  userCenterRoute
]
