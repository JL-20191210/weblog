<template>
    <div v-if="tags.length>0" 
        class="w-full py-5 px-2 mb-3 bg-white border border-gray-200 rounded-lg  dark:bg-gray-800 dark:border-gray-700">
        <h2 class="mb-2 font-bold flex items-center text-gray-900 uppercase dark:text-white">
            <svg t="1721919547356" class="icon w-4 h-4 mr-1.5" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5500" width="200" height="200"><path d="M209.1 651.9c-13.3 0-24.2-10.8-24.2-24.2v-80.5c0-13.3 10.8-24.2 24.2-24.2s24.2 10.8 24.2 24.2v80.5c0 13.4-10.8 24.2-24.2 24.2z" fill="#3778FF" p-id="5501"></path><path d="M305.8 795.1l188.1-200.4c9.1-9.8 26.1-9.8 35.2 0l47.3 50.4 31.7-33.8 181.5 193.4V112.2H305.8v682.9z" fill="#91B4FF" p-id="5502"></path><path d="M813.2 957.5c-6.6 0-13-2.7-17.6-7.6L510.9 646.5 226.1 949.9c-6.8 7.2-17.2 9.6-26.5 5.9-9.2-3.6-15.3-12.6-15.3-22.5V703.6c0-13.3 10.8-24.2 24.2-24.2s24.2 10.8 24.2 24.2v168.7l260.6-277.6c9.1-9.8 26.1-9.8 35.2 0l260.6 277.6V112.2H232.7V456c0 13.3-10.8 24.2-24.2 24.2s-24.2-10.8-24.2-24.2V88c0-13.3 10.8-24.2 24.2-24.2h604.7c13.3 0 24.2 10.8 24.2 24.2v845.3c0 9.9-6.1 18.8-15.3 22.5-2.9 1.2-5.9 1.7-8.9 1.7z" fill="#3778FF" p-id="5503"></path></svg>
            标签
        </h2>
        <!-- 标签列表 -->
        <span v-for="(tag, index) in tags" :key="index" @click="goTagArticleListPage(tag.id, tag.name)"
            class="inline-block mb-1 cursor-pointer bg-green-100 text-green-800 text-xs font-medium mr-2 px-2.5 py-0.5 rounded hover:bg-green-200 hover:text-green-900 dark:bg-green-900 dark:text-green-300 dark:hover:bg-green-950">
            {{ tag.name }}
        </span>
    </div>
</template>
<script setup>
import { getTagList } from '@/api/frontend/tag.js'
import { ref } from 'vue'
import { useRouter } from 'vue-router';

const router = useRouter()

// 所有标签
const tags = ref([])
getTagList().then((res) => {
    if (res.success) {
        tags.value = res.data
    }
})

// 跳转标签文章列表页
const goTagArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（标签 ID、标签名称）
    router.push({path: '/tag/article/list', query: {id, name}})
}
</script>