<template>
  <div id="app">
      <IndexPage></IndexPage>
  </div>
</template>

<script>
import IndexPage from './components/IndexPage.vue'

export default {
  name: 'App',
  components: {
    IndexPage
  }
}
</script>

<style>
#app {
  height: 100%;

}
</style>
