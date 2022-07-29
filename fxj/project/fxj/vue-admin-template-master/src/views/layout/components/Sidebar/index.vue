<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      text-color="#000000"
      font-size="30px"
      active-text-color="white"
      style="font-weight: bold;"
    >
      <sidebar-item v-for="route in routes" :key="route.path" :item="route" :base-path="route.path" class="sidebar_item"/>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    routes() {
      return this.$router.options.routes
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.sidebar_item {
  background: #52aeff;

  box-shadow: 4px 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0.5);
  //&:hover {
  //  background-color: #6bfff3 !important; // 子模块的hover背景色
  //}
}
</style>
