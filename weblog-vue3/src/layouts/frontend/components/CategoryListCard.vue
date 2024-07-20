<template>
    <div v-if="categories && categories.length>0" class="w-full py-5 px-2  bg-white border border-gray-200 rounded-lg  dark:bg-gray-800 dark:border-gray-700">
        <!-- 分类标题 -->
        <h2 class="mb-2 font-bold uppercase  text-gray-900 dark:text-white">分类</h2>
        <!-- 分类 -->
        <div
            class="w-48 text-sm font-medium text-gray-900 bg-white  rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
            <a v-for="(category,index) in categories" :key="index" href="#" aria-current="true"
                class="flex items-center w-full px-4 py-2  cursor-pointer rounded-lg hover:bg-gray-100 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-700  cursor-pointer dark:bg-gray-800 dark:border-gray-600">
                <svg class="w-4 h-4 mr-1.5  dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                    width="24" height="24" fill="none" viewBox="0 0 24 24">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M3 19V6a1 1 0 0 1 1-1h4.032a1 1 0 0 1 .768.36l1.9 2.28a1 1 0 0 0 .768.36H16a1 1 0 0 1 1 1v1M3 19l3-8h15l-3 8H3Z" />
                </svg>
                {{category.name}}
            </a>
        </div>
    </div>
</template>

<script setup>
import { getCategoryList } from '@/api/frontend/category'
import { ref } from 'vue'

// 所有分类
const categories = ref([])

getCategoryList().then((res)=>{
    if(res.success){
        categories.value = res.data
    }
})
</script>