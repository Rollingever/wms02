import Vue from 'vue'
import Vuex from 'vuex'
import router, { resetRouter } from "../router"

Vue.use(Vuex)

function addNewRoute(menuList) {
    let routes = router.options.routes
    routes.forEach(routeItem => {
        if (routeItem.path === "/IndexPage") {
            // if (!routeItem.children) {
            //     routeItem.children = []
            // }
            menuList.forEach(menu => {
                let childRoute = {
                    path: menu.menuclick,  // 确保这是正确的路径
                    name: menu.menuname,
                    meta: {
                        title: menu.menuname
                    },
                    component: () => import(`../components/${menu.menucomponent}`)
                }
                routeItem.children.push(childRoute)
            })
        }
    })

    resetRouter()
    router.addRoutes(routes)
}

export default new Vuex.Store({
    state: {
        menu: []
    },
    mutations: {
        setMenu(state, menuList) {
            state.menu = menuList
            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state.menu
        }
    }
})
