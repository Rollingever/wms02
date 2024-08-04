<template>
  <el-container style="height: 790px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu :default-openeds="['1', '3']">
        <!-- 导航和选项部分 -->
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-message"></i>导航一</template>
          <el-menu-item-group>
            <el-menu-item  @click="addTab('TabContent1', '选项1')">选项1</el-menu-item>
            <el-menu-item index="1-2" @click="addTab('TabContent2', '选项2')">选项2</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template slot="title">选项3</template>
            <el-menu-item index="1-4-1" @click="addTab('TabContent4', '选项3-1')">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <!-- 其他导航部分 -->
      </el-menu>
    </el-aside>

    <el-container style="height: 100%;">
      <el-header style="text-align: right; font-size: 12px">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>查看</el-dropdown-item>
            <el-dropdown-item>新增</el-dropdown-item>
            <el-dropdown-item>删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span>王小虎</span>
      </el-header>

      <el-main style="height: 100%;">
        <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab">
          <el-tab-pane
              v-for="tab in editableTabs"
              :key="tab.name"
              :label="tab.title"
              :name="tab.name"
          >
            <component :is="tab.component"></component>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
.el-main {
  padding: 5px;
}
.el-aside {
  color: #333;
}
</style>

<script>
import TabContent1 from "@/components/TabContent1.vue";


export default {
  name: "IndexPage",
  components: {
    TabContent1,

  },
  data() {
    return {
      editableTabsValue: '1',
      editableTabs: [
        {
          title: 'Tab 1',
          name: '1',
          component: 'TabContent1'
        },
        {
          title: 'Tab 2',
          name: '2',
          component: 'TabContent2'
        }
      ],
      tabIndex: 2
    }
  },
  methods: {
    addTab(component, title) {
      let newTabName = ++this.tabIndex + '';
      this.editableTabs.push({
        title: title,
        name: newTabName,
        component: component
      });
      this.editableTabsValue = newTabName;
    },
    removeTab(targetName) {
      let tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
        });
      }

      this.editableTabsValue = activeName;
      this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    }
  }
}
</script>
