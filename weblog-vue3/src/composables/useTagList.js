import { ref } from "vue";
import { useMenuStore } from "@/stores/menu";
import { useRoute, useRouter, onBeforeRouteUpdate } from 'vue-router';
import { setTabList, getTabList } from '@/composables/cookie';

export function useTabList() {
    const route = useRoute();
    const router = useRouter();
    const activeTab = ref(route.path);
    const menuStore = useMenuStore();
    const tabList = ref([
        {
            title: "仪表盘",
            path: "/admin/index"
        }
    ]);

    // 删除Tab标签页
    const removeTab = (path) => {
        let tabs = tabList.value

        // 当前被选中的Tab标签
        let actTab = activeTab.value

        // 如果要删除的是当前被选中的标签页，则需要判断其被删除后，需要激活哪个 tab 标签页
        if (actTab == path) {
            // 循环 tabList
            tabs.forEach((tab, index) => {
                // 获取被选中的 tab 元素
                if (tab.path == path) {
                    // 拿到被选中的标签页下标，如果它后面还有标签页，则取下一个标签页，否则取上一个
                    let nextTab = tabs[index + 1] || tabs[index - 1]
                    if (nextTab) {
                        actTab = nextTab.path
                    }
                }
            })
        }

        activeTab.value = actTab

        tabList.value = tabList.value.filter((tab) => tab.path != path)

        setTabList(tabList.value)

        tabChange(activeTab.value)
    };

    // 处理关闭标签菜单事件
    const handleCloseTab = (command) => {

        // 首页路由
        let indexPath = '/admin/index'

        // 处理关闭其他
        if (command == 'closeOthers') {
            // 仅过滤出首页和当前页
            tabList.value = tabList.value.filter((tab) => tab.path == indexPath || tab.path == activeTab.value)
        } else if (command == 'closeAll') {// 处理关闭全部
            // 切换回首页
            activeTab.value = indexPath
            // 只保留首页
            tabList.value = tabList.value.filter((tab) => tab.path == indexPath)
            // 切换标签页
            tabChange(activeTab.value)
        }

        // 设置到 cookie 中
        setTabList(tabList.value)
    }

    // 标签页切换事件
    const tabChange = (path) => {
        // 设置被激活的Tab标签
        activeTab.value = path
        // 路由跳转
        router.push(path)
    }

    //添加tab标签
    function addTab(tab) {
        // 标签是否不存在
        let isTabNotExisted = tabList.value.findIndex(item => item.path == tab.path) == -1
        if (isTabNotExisted) {
            tabList.value.push(tab)
        }

        // 存储tabList到Cookie中
        setTabList(tabList.value)
    }

    function initTabList() {
        let tabs = getTabList();
        if (tabs) {
            tabList.value = tabs;
        }
    }

    // 初始化标签导航栏
    initTabList()

    //在路由切换前被调用
    onBeforeRouteUpdate((to, from) => {
        // 设置激活的Tab标签
        activeTab.value = to.path

        // 添加Tab标签页
        addTab({
            title: to.meta.title,
            path: to.path
        });

    })

    return { menuStore, activeTab, tabList, tabChange, removeTab, handleCloseTab }
}