import Vue from 'vue';
import VueRouter from 'vue-router'; // 使用 VueRouter 而不是 Router
import TabContent1 from '@/components/TabContent1.vue';
import IndexPage from '@/components/IndexPage.vue';

Vue.use(VueRouter); // 使用 VueRouter

const routes = [
    {
        path: '/',
        redirect: '/login' // 确保重定向到小写的 /login
    },
    {
        path: '/login', // 使用小写路径
        name: 'Login',
        component: () => import('../components/AppLogin.vue')
    },
    {
        path: '/indexpage', // 使用小写路径
        name: 'IndexPage',
        component:IndexPage
    },
    {
        path: '/tab1',
        name: 'TabContent1',
        component: TabContent1
    },
];

const router = new VueRouter({ // 使用 VueRouter 而不是 Router
    mode: 'history',
    routes
});

export function resetRouter() {
    router.matcher = new VueRouter({
        mode: 'history',
        routes: []
    }).matcher;
}

const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err);
}

export default router;
