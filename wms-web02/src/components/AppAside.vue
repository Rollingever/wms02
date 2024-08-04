<template>
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="height: 100vh;"
      default-active="/IndexPage/AppHome"
      :collapse="isCollapse"
      :collapse-transition="false"
      router>
    <el-menu-item index="/IndexPage/AppHome">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item
        v-for="(item, i) in menu"
        :key="i"
        :index="'/IndexPage/' + item.menuclick">
      <i :class="item.menuicon"></i>
      <span slot="title">{{ item.menuname }}</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "AppAside",
  props: {
    isCollapse: Boolean,
  },
  computed: {
    menu() {
      return this.$store.getters.getMenu;
    }
  }
};
</script>

<style scoped>
</style>
