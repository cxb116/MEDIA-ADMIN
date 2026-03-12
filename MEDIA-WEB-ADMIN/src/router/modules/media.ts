import { AppRouteRecord } from '@/types/router'

/**
 * 应用管理（一级菜单）
 */
export const mediaAppRoutes: AppRouteRecord = {
  path: '/media/app',
  name: 'MediaApp',
  component: '/media/app',
  meta: {
    title: 'menus.mediaApp.title',
    icon: 'ri:apps-2-line',
    keepAlive: true,
    roles: ['R_SUPER', 'R_ADMIN']
  }
}

/**
 * 广告位管理（一级菜单）
 */
export const mediaSlotRoutes: AppRouteRecord = {
  path: '/media/slot',
  name: 'MediaSlot',
  component: '/media/slot',
  meta: {
    title: 'menus.mediaSlot.title',
    icon: 'ri:layout-grid-line',
    keepAlive: true,
    roles: ['R_SUPER', 'R_ADMIN']
  }
}

/**
 * 数据查询（一级菜单）
 */
export const mediaDataRoutes: AppRouteRecord = {
  path: '/media/data',
  name: 'MediaData',
  component: '/media/data',
  meta: {
    title: 'menus.mediaData.title',
    icon: 'ri:bar-chart-box-line',
    keepAlive: true,
    roles: ['R_SUPER', 'R_ADMIN']
  }
}

// 导出所有媒体管理相关路由（兼容旧代码）
export const mediaRoutes: AppRouteRecord[] = [
  mediaAppRoutes,
  mediaSlotRoutes,
  mediaDataRoutes
]
