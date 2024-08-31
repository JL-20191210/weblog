<template>
    <div v-if="categories && categories.length > 0"
        class="w-full py-5 px-2  bg-white border border-gray-200 rounded-lg  dark:bg-gray-800 dark:border-gray-700">
        <!-- 分类标题 -->
        <h2 class="mb-2 font-bold uppercase flex items-center  text-gray-900 dark:text-white">
            <svg t="1721919227855" class="icon w-4 h-4 mr-1.5" viewBox="0 0 1024 1024" version="1.1"
                xmlns="http://www.w3.org/2000/svg" p-id="4458" width="200" height="200">
                <path
                    d="M121.018182 488.727273L211.781818 139.636364h600.436364l90.763636 349.090909H744.727273c-51.2 0-93.090909 41.890909-93.090909 93.090909h372.363636L902.981818 116.363636c-11.636364-41.890909-48.872727-69.818182-90.763636-69.818181H211.781818C169.890909 46.545455 132.654545 74.472727 121.018182 116.363636L0 581.818182h372.363636c0-51.2-41.890909-93.090909-93.090909-93.090909H121.018182z"
                    fill="#176AFD" p-id="4459"></path>
                <path
                    d="M930.909091 581.818182v279.272727c0 13.963636-9.309091 23.272727-23.272727 23.272727H116.363636c-13.963636 0-23.272727-9.309091-23.272727-23.272727V581.818182h186.181818v116.363636c0 25.6 20.945455 46.545455 46.545455 46.545455h372.363636c25.6 0 46.545455-20.945455 46.545455-46.545455v-116.363636h186.181818m13.963636-93.090909H744.727273c-51.2 0-93.090909 41.890909-93.090909 93.090909v69.818182H372.363636v-69.818182c0-51.2-41.890909-93.090909-93.090909-93.090909H79.127273C34.909091 488.727273 0 523.636364 0 567.854545V861.090909c0 65.163636 51.2 116.363636 116.363636 116.363636h791.272728c65.163636 0 116.363636-51.2 116.363636-116.363636V567.854545c0-44.218182-34.909091-79.127273-79.127273-79.127272z"
                    fill="#1D2633" p-id="4460"></path>
            </svg>
            分类
        </h2>
        <!-- 分类列表 -->
        <div
            class="text-sm font-medium text-gray-600 bg-white rounded-lg dark:bg-gray-800 dark:border-gray-600 dark:text-white">
            <a @click="goCategoryArticleListPage(category.id, category.name)" v-for="(category, index) in categories"
                :key="index" aria-current="true"
                class="flex items-center w-full px-4 py-2  cursor-pointer rounded-lg hover:bg-gray-100 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-700  cursor-pointer dark:bg-gray-800 dark:border-gray-600">
                <svg class="w-4 h-4 mr-1.5  dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                    width="24" height="24" fill="none" viewBox="0 0 24 24">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M3 19V6a1 1 0 0 1 1-1h4.032a1 1 0 0 1 .768.36l1.9 2.28a1 1 0 0 0 .768.36H16a1 1 0 0 1 1 1v1M3 19l3-8h15l-3 8H3Z" />
                </svg>
                {{ category.name }}
            </a>
        </div>
    </div>
</template>

<script setup>
import { getCategoryList } from '@/api/frontend/category'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
// 引入路由
const router = useRouter()

// 跳转分类文章列表页
const goCategoryArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（分类 ID、分类名称）
    router.push({ path: '/category/article/list', query: { id, name } })
}

// 所有分类
const categories = ref([])

getCategoryList().then((res) => {
    if (res.success) {
        categories.value = res.data
    }
})

</script>