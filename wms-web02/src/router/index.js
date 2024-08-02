import Vue from 'vue';
import Router from 'vue-router';
import TabContent1 from '@/components/TabContent1.vue';


Vue.use(Router);

const routes = [
    {
        path: '/tab1',
        name: 'TabContent1',
        component: TabContent1
    },

];

const router = new Router({
    routes
});

export default router;
