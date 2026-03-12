import { AppRouteRecord } from '@/types/router'

/**
 * 个人中心（隐藏路由，不在菜单中显示，通过顶部用户菜单访问）
 */
export const userCenterRoute: AppRouteRecord = {
  path: '/system/user-center',
  name: 'UserCenter',
  component: '/system/user-center',
  meta: {
    title: 'menus.system.userCenter',
    isHide: true,
    keepAlive: true,
    isHideTab: true
  }
}
