import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* 添加路由信息
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/

export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    // 主页的路由，路径为'/'时重定向到/dashboard
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },
  {
    /**
     * 个人信息的路由
     */
    path: '/userInfo', // 路径信息
    component: Layout, // 加载组件
    redirect: '/userInfo/baseInfo', // 重定向的请求
    name: 'UserInfo',
    meta: { title: 'UserInfo', icon: '用户' }, // 设置名称和图标
    children: [
      {
        path: 'baseInfo', // 子路径在父路径基础上叠加
        name: 'BaseInfo',
        component: () => import('@/views/userInfo/baseInfo'), // 箭头函数指向路由跳转的页面
        meta: { title: 'Base', icon: '用户信息' }
      },
      {
        path: 'changeBaseInfo',
        name: 'ChangeInfo',
        component: () => import('@/views/userInfo/changeBaseInfo'),
        meta: { title: 'Change', icon: '修改用户' }
      },
      {
        path: 'model',
        name: 'mode',
        component: () => import('@/views/userInfo/model'),
        meta: { title: 'Mode', icon: '修改用户' }
      }
    ]
  },
  {
    /**
     * 记忆的路由
     */
    path: '/memory', // 路径信息
    component: Layout, // 加载组件
    redirect: '/memory/photo', // 重定向的请求
    name: 'Memory',
    meta: { title: 'Memory', icon: '回忆' }, // 设置名称和图标
    children: [
      {
        path: 'photo', // 子路径在父路径基础上叠加
        name: 'Photo',
        component: () => import('@/views/memory/photo'), // 箭头函数指向路由跳转的页面
        meta: { title: 'Photo', icon: '相册' }
      },
      {
        path: 'view',
        name: 'View',
        component: () => import('@/views/memory/record'),
        meta: { title: 'Record', icon: 'tree' }
      }
    ]
  },

  {
    /**
     * 事件记录的路由
     */
    path: '/event', // 路径信息
    component: Layout, // 加载组件
    redirect: '/event/task', // 重定向的请求 也就是说我在event模块下点击请求为/event时默认就转为/event/task
    name: 'Event',
    meta: { title: 'Event', icon: '事件' }, // 设置名称和图标
    children: [
      {
        path: 'task', // 子路径在父路径基础上叠加
        name: 'Task',
        component: () => import('@/views/event/task'), // 箭头函数指向路由跳转的页面
        meta: { title: 'Task', icon: '任务' },
        affix: true
      },
      {
        path: 'view',
        name: 'View',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Record', icon: 'tree' }
      },
      {
        path: 'task/board',
        name: 'Board',
        hidden: true,
        component: () => import('@/views/event/taskBoard'),
        meta: { title: 'Board' }
      }
    ]
  },
  // {
  //   path: '/example', // 路径信息
  //   component: Layout, // 加载组件
  //   redirect: '/example/table',
  //   name: 'Example',
  //   meta: { title: 'Example', icon: 'example' },
  //   children: [
  //     {
  //       path: 'table',
  //       name: 'Table',
  //       component: () => import('@/views/table/index'),
  //       meta: { title: 'Table', icon: 'table' }
  //     },
  //     {
  //       path: 'tree',
  //       name: 'Tree',
  //       component: () => import('@/views/tree/index'),
  //       meta: { title: 'Tree', icon: 'tree' }
  //     }
  //   ]
  // },
  // {
  //   path: '/form',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Form',
  //       component: () => import('@/views/form/index'),
  //       meta: { title: 'Form', icon: 'form' }
  //     }
  //   ]
  // },
  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
  //       meta: { title: 'External Link', icon: 'link' }
  //     }
  //   ]
  // },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
