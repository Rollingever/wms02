<template>
  <el-main style="height: 100%;">
    <el-tabs v-model="localEditableTabsValue" type="card" closable @tab-remove="removeTab">
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
</template>


<script>
import TabContent1 from "@/components/TabContent1.vue";
import TabContent2 from "@/components/TabContent2.vue";
import AppHome from "@/components/AppHome.vue";

export default {
  name: "AppMain",
  components: {
    AppHome,
    TabContent1,
    TabContent2,
  },
  props: {
    editableTabs: {
      type: Array,
      required: true,
    },
    editableTabsValue: {
      type: String,
      required: true,
    },
  },
  computed: {
    localEditableTabsValue: {
      get() {
        return this.editableTabsValue;
      },
      set(value) {
        this.$emit('update:editableTabsValue', value);
      }
    }
  },
  methods: {
    removeTab(targetName) {
      this.$emit('remove-tab', targetName);
    }
  }
}
</script>

<style scoped>
.el-main {
  padding: 5px;
}
</style>
