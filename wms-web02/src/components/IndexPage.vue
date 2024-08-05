<template>
  <el-container style="height: 790px; border: 1px solid #eee">
    <el-header style="text-align: right; font-size: 12px">
      <el-dropdown>
        <span class="el-dropdown-link">
        {{ userName }}<i class="el-icon-arrow-down" style="margin-left:20px"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>

    <el-container style="height: 100%;">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <AppAside @add-tab="addTab"></AppAside>
      </el-aside>

      <AppMain
          :editableTabs="editableTabs"
          :editableTabsValue.sync="editableTabsValue"
          @remove-tab="removeTab"
      ></AppMain>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
  width: 100%;
}
.el-main {
  padding: 5px;
}
.el-aside {
  color: #333;
}
</style>

<script>
import AppAside from "@/components/AppAside.vue";
import AppMain from "@/components/AppMain.vue";

export default {
  name: "IndexPage",
  components: {
    AppMain,
    AppAside,

  },
  data() {
    let user = null;
    try {
      const userData = sessionStorage.getItem('CurUser');
      user = userData ? JSON.parse(userData) : null;
    } catch (e) {
      console.error('从 sessionStorage 解析用户数据时出错', e);
    }
    return {
      user: user || {}, // 确保 user 属性始终被定义为一个对象
      editableTabsValue: '1',
      editableTabs: [
        {
          title: '个人中心',
          name: '1',
          component: 'AppHome'
        },
      ],
      tabIndex: 2
    }
  },
  computed: {
    userName() {
      return this.user.name || 'Guest';
    }
  },
  methods: {
    logout() {
      console.log('logout');
      this.$confirm('您确定要退出登录吗？','提示',{
        type: 'warning',
        confirmButtonText:'确定',
        center: true
      })
          .then(()=>{
            this.$message({
              type: 'success',
              message:'退出登录成功'
            });
            this.$router.push('/');
            sessionStorage.clear();
          })
          .catch(()=>{
            this.$message({
              type: 'info',
              message:'已取消退出登录'
            });
          });
    },
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