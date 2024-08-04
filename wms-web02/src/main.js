import Vue from 'vue';
import App from './App.vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from 'axios';
import VueRouter from 'vue-router';
import router from './router';
import store from './store';


Vue.prototype.$axios = axios;
Vue.prototype.$httpUrl = "http://localhost:8090"
Vue.use(VueRouter);
Vue.use(ElementUI, { size: 'small' });
Vue.config.productionTip = false

const vm = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');

// 暴露 router 和 store 到全局
window.router = router;
window.store = store;
window.vm = vm;
