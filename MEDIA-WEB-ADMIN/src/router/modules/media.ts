import { AppRouteRecord } from '@/types/router'

export const mediaRoutes: AppRouteRecord = {
  path: '/media',
  name: 'Media',
  component: '/index/index',
  meta: {
    title: 'menus.media.title',
    icon: 'ri:apps-line',
    roles: ['R_SUPER', 'R_ADMIN']
  },
  children: [
    {
      path: 'app',
      name: 'MediaApp',
      component: '/media/app',
      meta: {
        title: 'menus.media.app',
        keepAlive: true,
        roles: ['R_SUPER', 'R_ADMIN']
      }
    },
    {
      path: 'slot',
      name: 'MediaSlot',
      component: '/media/slot',
      meta: {
        title: 'menus.media.slot',
        keepAlive: true,
        roles: ['R_SUPER', 'R_ADMIN']
      }
    }
  ]
}
