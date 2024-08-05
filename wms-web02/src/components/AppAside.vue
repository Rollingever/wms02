<template>
  <el-menu :default-openeds="['1', '3']">
    <el-menu-item @click="$emit('add-tab', 'AppHome', '个人中心')">个人中心</el-menu-item>
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-message"></i>导航一</template>
      <el-menu-item-group>
        <el-menu-item @click="$emit('add-tab', 'TabContent1', '选项1')">选项1</el-menu-item>
        <el-menu-item index="1-2" @click="$emit('add-tab', 'TabContent2', '选项2')">选项2</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="1-4">
        <template slot="title">选项3</template>
        <el-menu-item index="1-4-1" @click="$emit('add-tab', 'TabContent4', '选项3-1')">选项4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
    <!-- 其他导航部分 -->
  </el-menu>
</template>

<script>
export default {
  name: "AppAside",
};
</script>

<style scoped>
.el-aside {
  background-color: rgb(238, 241, 246);
  color: #333;
}
</style>
