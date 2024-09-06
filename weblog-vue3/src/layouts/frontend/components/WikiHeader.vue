<template>
    <header class="sticky top-0 z-30">
        <nav class="bg-white border-gray-200 border-b dark:bg-[#0d1117] dark:border-gray-800">
            <div class="max-w-screen-2xl flex flex-wrap items-center justify-between mx-auto p-4">
                <div class="flex items-center">
                    <!-- 移动端知识库目录图标 -->
                    <div class="inline-block lg:hidden mr-3">
                        <input type="checkbox" id="checkbox" v-model="isDrawerExpand">
                        <label for="checkbox" class="toggle">
                            <div class="bars" id="bar1"></div>
                            <div class="bars" id="bar2"></div>
                            <div class="bars" id="bar3"></div>
                        </label>
                    </div>

                    <!-- 博客 LOGO 、博客名称 -->
                    <a href="/" class="flex items-center">
                        <img :src="blogSettingsStore.blogSettings.logo" class="h-8 mr-3 rounded-full"
                            alt="Weblog Logo" />
                        <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-gray-400">{{
                            blogSettingsStore.blogSettings.name }}</span>
                    </a>
                </div>
                <div class="flex md:order-2 items-center">
                    <!-- 白天黑夜切换 -->
                    <button @click="toggleDark()" class="ml-1 mr-4 vt-switch vt-switch-appearance" type="button"
                        role="switch" aria-label="切换深色模式" aria-checked="false" data-v-d401ce6f=""><span
                            class="vt-switch-check"><span class="vt-switch-icon"><!--[-->
                                <!-- 太阳图标 -->
                                <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false"
                                    viewBox="0 0 24 24" class="vt-switch-appearance-sun"
                                    :class="[isDark ? 'hidden' : 'block']">
                                    <path
                                        d="M12,18c-3.3,0-6-2.7-6-6s2.7-6,6-6s6,2.7,6,6S15.3,18,12,18zM12,8c-2.2,0-4,1.8-4,4c0,2.2,1.8,4,4,4c2.2,0,4-1.8,4-4C16,9.8,14.2,8,12,8z">
                                    </path>
                                    <path d="M12,4c-0.6,0-1-0.4-1-1V1c0-0.6,0.4-1,1-1s1,0.4,1,1v2C13,3.6,12.6,4,12,4z">
                                    </path>
                                    <path
                                        d="M12,24c-0.6,0-1-0.4-1-1v-2c0-0.6,0.4-1,1-1s1,0.4,1,1v2C13,23.6,12.6,24,12,24z">
                                    </path>
                                    <path
                                        d="M5.6,6.6c-0.3,0-0.5-0.1-0.7-0.3L3.5,4.9c-0.4-0.4-0.4-1,0-1.4s1-0.4,1.4,0l1.4,1.4c0.4,0.4,0.4,1,0,1.4C6.2,6.5,5.9,6.6,5.6,6.6z">
                                    </path>
                                    <path
                                        d="M19.8,20.8c-0.3,0-0.5-0.1-0.7-0.3l-1.4-1.4c-0.4-0.4-0.4-1,0-1.4s1-0.4,1.4,0l1.4,1.4c0.4,0.4,0.4,1,0,1.4C20.3,20.7,20,20.8,19.8,20.8z">
                                    </path>
                                    <path d="M3,13H1c-0.6,0-1-0.4-1-1s0.4-1,1-1h2c0.6,0,1,0.4,1,1S3.6,13,3,13z"></path>
                                    <path d="M23,13h-2c-0.6,0-1-0.4-1-1s0.4-1,1-1h2c0.6,0,1,0.4,1,1S23.6,13,23,13z">
                                    </path>
                                    <path
                                        d="M4.2,20.8c-0.3,0-0.5-0.1-0.7-0.3c-0.4-0.4-0.4-1,0-1.4l1.4-1.4c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4l-1.4,1.4C4.7,20.7,4.5,20.8,4.2,20.8z">
                                    </path>
                                    <path
                                        d="M18.4,6.6c-0.3,0-0.5-0.1-0.7-0.3c-0.4-0.4-0.4-1,0-1.4l1.4-1.4c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4l-1.4,1.4C18.9,6.5,18.6,6.6,18.4,6.6z">
                                    </path>
                                </svg>
                                <!-- 黑夜图标 -->
                                <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false"
                                    viewBox="0 0 24 24" class="vt-switch-appearance-moon"
                                    :class="[isDark ? 'block' : 'hidden']">
                                    <path
                                        d="M12.1,22c-0.3,0-0.6,0-0.9,0c-5.5-0.5-9.5-5.4-9-10.9c0.4-4.8,4.2-8.6,9-9c0.4,0,0.8,0.2,1,0.5c0.2,0.3,0.2,0.8-0.1,1.1c-2,2.7-1.4,6.4,1.3,8.4c2.1,1.6,5,1.6,7.1,0c0.3-0.2,0.7-0.3,1.1-0.1c0.3,0.2,0.5,0.6,0.5,1c-0.2,2.7-1.5,5.1-3.6,6.8C16.6,21.2,14.4,22,12.1,22zM9.3,4.4c-2.9,1-5,3.6-5.2,6.8c-0.4,4.4,2.8,8.3,7.2,8.7c2.1,0.2,4.2-0.4,5.8-1.8c1.1-0.9,1.9-2.1,2.4-3.4c-2.5,0.9-5.3,0.5-7.5-1.1C9.2,11.4,8.1,7.7,9.3,4.4z">
                                    </path>
                                </svg><!--]--></span></span></button>
                    <!-- 搜索框 -->
                    <button type="button" @click="clickSearchBtn" class="mr-2 hidden outline-none md:flex items-center text-sm leading-6 text-slate-400 rounded-md 
                        ring-1 ring-slate-900/10 shadow-sm py-1.5 pl-2 pr-3 hover:ring-slate-300 
                        dark:bg-slate-800 dark:hover:bg-slate-700 dark:hover:ring-slate-700">
                        <svg class="w-3.5 h-3.5 mr-2 text-gray-500 dark:text-gray-400" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
                        </svg>
                        <span class="mr-3">搜索文章 ...</span>
                        <span
                            class="px-2 py-[1px] flex-none text-xs border text-gray-400 rounded dark:border-gray-600">Ctrl
                            K</span>
                    </button>
                    <!-- 登录 -->
                    <div class="text-gray-900 ml-1 mr-1 hover:text-blue-700 dark:text-white"
                        @click="$router.push('/login')" v-if="!isLogined">登录</div>

                    <!-- 已经登录，展示用户头像 -->
                    <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown" v-else
                        class="text-white ml-2 focus:ring-4 mr-2 md:mr-0 focus:ring-blue-300 font-medium rounded-full text-sm text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                        type="button">
                        <img class="w-8 h-8 rounded-full" :src="blogSettingsStore.blogSettings.avatar">
                    </button>

                    <!-- Dropdown menu -->
                    <div id="dropdown"
                        class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                        <ul class="py-2 text-sm text-gray-700 dark:text-gray-200"
                            aria-labelledby="dropdownDefaultButton">
                            <li>
                                <a @click="router.push('/admin/index')"
                                    class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                                    <svg class="inline w-3 h-3 mb-[2px] mr-1 text-gray-700 dark:text-white"
                                        aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                        viewBox="0 0 20 20">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="2"
                                            d="M10 14v4m-4 1h8M1 10h18M2 1h16a1 1 0 0 1 1 1v11a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1Z" />
                                    </svg>
                                    进入后台</a>
                            </li>
                            <li>
                                <a data-modal-target="popup-modal" data-modal-toggle="popup-modal"
                                    class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                                    <svg class="inline w-3 h-3 mb-[2px] mr-1 text-gray-700 dark:text-white"
                                        aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                        viewBox="0 0 16 16">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="2"
                                            d="M4 8h11m0 0-4-4m4 4-4 4m-5 3H3a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h3" />
                                    </svg>
                                    退出登录</a>
                            </li>
                        </ul>
                    </div>
                    <button data-collapse-toggle="navbar-search" type="button"
                        class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
                        aria-controls="navbar-search" aria-expanded="false">
                        <span class="sr-only">Open main menu</span>
                        <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                            viewBox="0 0 17 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M1 1h15M1 7h15M1 13h15" />
                        </svg>
                    </button>
                </div>
                <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-search">
                    <!-- 移动端搜索框 -->
                    <button type="button" @click="clickSearchBtn" class="relative mt-3 flex w-full md:hidden items-center text-sm leading-6 text-slate-400 rounded-md
                         ring-1 ring-slate-900/10 shadow-sm py-1.5 pl-2 pr-3 hover:ring-slate-300 dark:bg-slate-800 
                         dark:highlight-white/5 dark:hover:bg-slate-700 dark:hover:ring-slate-700">
                        <svg class="w-3.5 h-3.5 mr-2 text-gray-500 dark:text-gray-400" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
                        </svg>
                        <span class="mr-3 grow text-left">搜索文章 ...</span>
                        <span
                            class="px-2 py-[1px] flex-none text-xs border text-gray-400 rounded dark:border-gray-600">Ctrl
                            K</span>
                    </button>


                    <ul
                        class="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 md:mt-0 md:border-0 md:bg-white  md:dark:bg-gray-900 dark:border-gray-700">
                        <li>
                            <a @click="router.push('/')"
                                :class="[currPath == '/' ? 'text-sky-600 md:border-b-2 md:border-sky-600 dark:text-sky-500 dark:md:border-sky-600' : 'text-gray-900 dark:text-gray-400']"
                                class="cursor-pointer block py-2 pl-3 pr-4 rounded md:rounded-none hover:bg-gray-100 md:hover:bg-transparent md:hover:text-sky-600 md:bg-transparent md:p-0 dark:hover:bg-gray-700 md:dark:hover:bg-transparent"
                                aria-current="page">首页</a>
                        </li>
                        <li>
                            <a @click="router.push('/category/list')"
                                :class="[currPath.startsWith('/category') ? 'text-sky-600 md:border-b-2 md:border-sky-600 dark:text-sky-500 dark:md:border-sky-600' : 'text-gray-900 dark:text-gray-400']"
                                class="cursor-pointer block py-2 pl-3 pr-4 rounded md:rounded-none hover:bg-gray-100 md:hover:bg-transparent md:hover:text-sky-600 md:p-0 md:dark:hover:text-sky-500  dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">分类</a>
                        </li>
                        <li>
                            <a @click="router.push('/tag/list')"
                                :class="[currPath.startsWith('/tag') ? 'text-sky-600 md:border-b-2 md:border-sky-600 dark:text-sky-500 dark:md:border-sky-600' : 'text-gray-900 dark:text-gray-400']"
                                class="cursor-pointer block py-2 pl-3 pr-4 rounded md:rounded-none hover:bg-gray-100 md:hover:bg-transparent md:hover:text-sky-600 md:p-0 md:dark:hover:text-sky-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">标签</a>
                        </li>
                        <li>
                            <a @click="router.push('/archive/list')"
                                :class="[currPath == '/archive/list' ? 'text-sky-600 md:border-b-2 md:border-sky-600 dark:text-sky-500 dark:md:border-sky-600' : 'text-gray-900 dark:text-gray-400']"
                                class="cursor-pointer block py-2 pl-3 pr-4 rounded md:rounded-none hover:bg-gray-100 md:hover:bg-transparent md:hover:text-sky-600 md:p-0 md:dark:hover:text-sky-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">归档</a>
                        </li>
                        <li>
                            <a @click="router.push('/wiki/list')"
                                :class="[currPath.startsWith('/wiki') ? 'text-sky-600 md:border-b-2 md:border-sky-600 dark:text-sky-500 dark:md:border-sky-600' : 'text-gray-900 dark:text-gray-400']"
                                class="block py-2 pl-3 pr-4 rounded md:rounded-none hover:bg-gray-100 md:hover:bg-transparent md:hover:text-sky-600 md:p-0 md:dark:hover:text-sky-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700 dark:text-gray-400">知识库</a>
                        </li>

                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <!-- 退出登录 -->
    <div id="popup-modal" tabindex="-1"
        class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
        <div class="relative p-4 w-full max-w-md max-h-full">
            <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                <button type="button"
                    class="absolute top-3 end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                    data-modal-hide="popup-modal">
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                        viewBox="0 0 14 14">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                    </svg>
                    <span class="sr-only">Close modal</span>
                </button>
                <div class="p-4 md:p-5 text-center">
                    <svg class="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200" aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                    </svg>
                    <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">是否确认退出登录？</h3>
                    <button @click="logout" data-modal-hide="popup-modal" type="button"
                        class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center">
                        确定
                    </button>
                    <button data-modal-hide="popup-modal" type="button"
                        class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
                        取消</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 站内搜索 -->
    <div id="search-modal" tabindex="-1" aria-hidden="true"
        class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
        <div class="relative p-4 w-full max-w-2xl max-h-full">
            <!-- Modal content -->
            <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                <!-- Modal header -->
                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                    <form class="w-full mr-2">
                        <div class="relative">
                            <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <div v-if="searchLoading" role="status">
                                    <svg aria-hidden="true"
                                        class="w-4 h-4 text-gray-200 animate-spin dark:text-gray-600 fill-sky-600"
                                        viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path
                                            d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                            fill="currentColor" />
                                        <path
                                            d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                            fill="currentFill" />
                                    </svg>
                                    <span class="sr-only">Loading...</span>
                                </div>

                                <svg v-else class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
                                </svg>
                            </div>
                            <input type="search" id="search" ref="searchInputRef" v-model="searchWord"
                                class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-sky-500 focus:border-sky-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500"
                                placeholder="请输入关键词搜索..." required>
                        </div>
                    </form>

                    <button type="button" @click="searchModal.hide()"
                        class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                            viewBox="0 0 14 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                        </svg>
                        <span class="sr-only">Close modal</span>
                    </button>
                </div>
                <!-- Modal body -->
                <div class="p-4 md:p-5 space-y-4">
                    <div v-if="searchArticles && searchArticles.length > 0">
                        <p class="text-base leading-relaxed text-gray-500 dark:text-gray-400">
                            共搜索到 {{ total }} 篇相关文章
                        </p>
                        <ol class="mt-3 divide-y divider-gray-200 dark:divide-gray-600">
                            <li v-for="(article, index) in searchArticles" :key="index">
                                <a @click="jumpToArticleDetailPage(article.id)" class="items-center cursor-pointer block p-3 sm:flex hover:bg-gray-100 hover:rounded-lg 
                                    dark:hover:bg-gray-600">
                                    <img class="w-24 h-full mb-3 mr-3 rounded-lg sm:mb-0" :src="article.cover">
                                    <div class="text-gray-600 dark:text-gray-400">
                                        <h2 class="text-base font-normal text-gray-900 dark:text-white"
                                            v-html="article.title"></h2>
                                        <span
                                            class="inline-flex items-center text-xs font-normal text-gray-500 dark:text-gray-400">
                                            <svg class="inline w-2.5 h-2.5 mr-2 text-gray-400" aria-hidden="true"
                                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                                <path stroke="currentColor" stroke-linecap="round"
                                                    stroke-linejoin="round" stroke-width="2"
                                                    d="M5 1v3m5-3v3m5-3v3M1 7h18M5 11h10M2 3h16a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1Z">
                                                </path>
                                            </svg> {{ article.createDate }}</span>
                                    </div>
                                </a>
                            </li>
                        </ol>


                        <!-- 分页 -->
                        <div class="flex mt-7">
                            <a v-if="current > 1" @click="prePage" class="flex items-center justify-center px-3 h-8 me-3 text-xs font-medium text-gray-500 border 
                                border-gray-300 dark:border-gray-600 rounded-lg hover:bg-gray-100 hover:text-gray-700 
                                dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                                <svg class="w-3 h-3 rtl:rotate-180" aria-hidden="true"
                                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="M13 5H1m0 0 4 4M1 5l4-4" />
                                </svg>
                            </a>

                            <a v-if="current < pages" @click="nextPage" class="flex ml-auto items-center justify-center px-3 h-8 text-xs font-medium text-gray-500 
                                border border-gray-300 rounded-lg hover:bg-gray-100 hover:text-gray-700 dark:border-gray-600 
                             dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                                <svg class="w-3 h-3 rtl:rotate-180" aria-hidden="true"
                                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9" />
                                </svg>
                            </a>
                        </div>
                    </div>
                    <div v-else class="flex items-center justify-center flex-col mb-10">
                        <svg class="w-50 h-50" height="200" node-id="1" sillyvg="true" template-height="1000"
                            template-width="1287" version="1.1" viewBox="0 0 1287 1000" width="1287"
                            xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                            <defs node-id="194">
                                <clipPath id="clip-path" node-id="5">
                                    <path
                                        d="M 258.00 421.90 L 266.80 422.60 L 262.00 463.80 L 262.70 467.80 L 264.50 470.90 L 265.90 472.10 L 268.20 473.50 L 270.90 474.40 L 273.70 475.00 L 276.50 475.20 L 287.70 474.50 L 293.20 475.00 L 294.40 479.00 L 295.40 480.80 L 297.30 483.20 L 299.60 485.30 L 302.30 487.00 L 305.20 488.30 L 308.30 489.10 L 314.50 489.60 L 319.20 489.20 L 323.80 488.30 L 328.70 486.90 L 333.50 485.10 L 342.50 480.50 L 346.70 477.60 L 353.20 472.30 L 356.10 470.70 L 359.50 470.20 L 367.20 470.00 L 371.10 469.10 L 375.00 467.80 L 378.60 466.00 L 385.50 461.40 L 390.30 457.30 L 394.80 452.90 L 401.10 445.80 L 406.90 438.20 L 412.20 430.40 L 417.00 422.20 L 421.30 413.70 L 425.10 404.90 L 428.40 396.00 L 431.30 386.90 L 433.60 377.50 L 435.40 368.20 L 436.80 358.70 L 437.60 349.20 L 437.90 341.10 L 437.70 333.00 L 437.10 327.60 L 436.10 322.30 L 434.80 317.10 L 432.90 312.10 L 430.60 307.30 L 428.00 302.70 L 423.70 296.00 L 418.90 289.50 L 414.80 284.30 L 410.30 279.40 L 407.10 276.40 L 403.70 273.70 L 400.00 271.30 L 396.40 269.50 L 388.80 267.00 L 382.90 265.90 L 376.90 265.20 L 369.20 264.90 L 361.50 265.00 L 353.90 265.60 L 346.40 266.60 L 338.90 268.20 L 331.50 270.10 L 324.30 272.50 L 317.30 275.40 L 310.40 278.70 L 303.70 282.40 L 297.30 286.50 L 288.50 293.10 L 284.20 297.60 L 282.60 300.30 L 281.40 303.40 L 280.40 310.00 L 281.00 343.20 L 280.70 348.10 L 279.40 357.90 L 277.80 364.50 L 275.60 370.90 L 262.30 402.30 L 259.20 413.00 L 258.00 421.90 Z"
                                        fill="#000000" fill-rule="nonzero" node-id="197" stroke="none"
                                        target-height="224.70001" target-width="179.9" target-x="258"
                                        target-y="264.9" />
                                </clipPath>
                                <clipPath id="clip-path-2" node-id="7">
                                    <path
                                        d="M 509.90 364.80 L 507.50 356.10 L 504.60 347.50 L 501.10 339.20 L 497.00 331.10 L 493.10 324.50 L 489.00 318.20 L 484.60 312.20 L 479.90 306.50 L 474.90 301.20 L 468.30 294.90 L 461.30 289.10 L 454.00 283.80 L 446.40 279.00 L 438.50 274.70 L 430.40 270.90 L 422.00 267.70 L 413.40 265.00 L 404.70 262.90 L 395.90 261.30 L 386.90 260.20 L 377.80 259.80 L 368.70 259.90 L 359.50 260.60 L 350.40 261.90 L 341.20 263.80 L 332.20 266.30 L 323.30 269.40 L 314.40 273.10 L 305.60 277.60 L 297.10 282.60 L 289.10 288.00 L 281.70 293.80 L 274.70 300.00 L 268.10 306.70 L 262.10 313.70 L 256.60 321.00 L 251.60 328.60 L 247.00 336.50 L 243.00 344.60 L 239.60 352.90 L 236.60 361.40 L 234.30 370.10 L 232.40 378.90 L 231.20 387.70 L 230.50 396.70 L 230.40 405.70 L 231.00 414.70 L 232.10 423.70 L 233.80 432.70 L 235.60 439.80 L 237.80 446.80 L 240.50 453.70 L 243.50 460.60 L 247.00 467.50 L 251.60 475.30 L 256.70 482.80 L 262.40 489.90 L 268.50 496.70 L 278.30 497.90 L 288.10 498.60 L 297.70 498.70 L 307.20 498.30 L 316.70 497.40 L 326.20 496.00 L 335.60 494.30 L 344.90 492.00 L 354.20 489.40 L 363.40 486.30 L 374.70 482.00 L 386.00 477.10 L 397.20 471.70 L 419.00 459.80 L 429.40 453.40 L 439.50 446.70 L 449.30 439.40 L 458.60 431.80 L 467.60 423.70 L 474.00 417.30 L 480.10 410.70 L 485.90 403.80 L 491.40 396.60 L 496.60 389.10 L 501.40 381.30 L 505.80 373.30 L 509.90 364.80 Z"
                                        fill="#000000" fill-rule="nonzero" node-id="201" stroke="none"
                                        target-height="238.90002" target-width="279.5" target-x="230.4"
                                        target-y="259.8" />
                                </clipPath>
                            </defs>
                            <g node-id="585">
                                <g node-id="586">
                                    <g node-id="587">
                                        <g node-id="588">
                                            <path
                                                d="M 500.00 658.60 L 494.90 659.30 L 491.20 660.40 L 489.80 661.20 L 489.10 662.10 L 489.10 662.70 L 490.50 664.40 L 491.80 665.10 L 496.10 666.00 L 500.00 666.20 L 509.50 665.70 L 540.20 661.80 L 566.80 659.90 L 582.40 659.20 L 597.60 659.10 L 618.20 660.20 L 633.10 660.50 L 647.00 659.70 L 656.60 658.00 L 661.90 656.10 L 664.10 654.80 L 665.30 653.40 L 665.90 651.90 L 665.90 650.90 L 664.70 649.50 L 663.40 649.00 L 660.40 648.50 L 655.80 648.50 L 644.90 649.30 L 620.80 652.00 L 615.40 652.30 L 601.30 651.80 L 585.80 651.90 L 546.70 653.40 L 518.50 655.50 L 504.40 657.50 L 500.00 658.60 Z"
                                                fill="#bcbec0" fill-opacity="0.5" fill-rule="nonzero" group-id="1,2,3,4"
                                                node-id="212" stroke="none" target-height="17.700012"
                                                target-width="176.80002" target-x="489.1" target-y="648.5" />
                                        </g>
                                        <path
                                            d="M 669.50 615.70 L 668.70 614.10 L 667.50 612.80 L 666.10 611.80 L 663.50 610.70 L 660.60 610.10 L 657.50 609.80 L 651.60 610.20 L 645.80 611.70 L 642.50 613.20 L 639.40 615.00 L 635.00 618.20 L 622.70 629.30 L 618.50 632.70 L 614.10 635.60 L 610.90 637.20 L 607.60 638.40 L 603.90 639.10 L 601.00 639.20 L 598.10 638.90 L 595.30 638.10 L 591.10 636.50 L 575.80 628.10 L 572.30 626.80 L 568.90 626.00 L 565.40 625.70 L 561.70 625.80 L 557.70 626.50 L 552.50 628.20 L 547.40 630.40 L 542.70 633.10 L 529.80 643.20 L 526.80 645.00 L 523.80 646.30 L 517.60 647.40 L 501.00 647.80 L 495.90 649.00 L 493.70 650.10 L 491.70 652.30 L 491.10 653.70 L 490.80 655.60 L 491.10 657.40 L 493.90 660.10 L 498.40 661.20 L 507.60 661.60 L 511.60 661.40 L 515.70 660.80 L 519.80 659.80 L 527.70 657.00 L 543.60 649.00 L 549.90 646.10 L 556.20 643.70 L 560.50 642.50 L 564.80 641.80 L 569.20 641.50 L 575.70 642.30 L 579.20 643.20 L 582.80 644.60 L 596.50 651.40 L 603.80 653.90 L 607.20 654.30 L 611.20 654.20 L 615.20 653.60 L 619.20 652.50 L 623.00 651.00 L 626.00 649.30 L 628.70 647.30 L 631.20 645.00 L 640.40 634.80 L 643.30 632.00 L 645.90 630.20 L 648.60 628.80 L 651.50 628.10 L 654.70 627.80 L 661.70 627.80 L 664.70 627.10 L 667.60 626.00 L 669.50 624.20 L 670.40 621.40 L 670.30 619.40 L 669.50 615.70 Z"
                                            fill="#8055bf" fill-rule="nonzero" group-id="1,2,3" node-id="215"
                                            stroke="none" target-height="51.799988" target-width="179.60004"
                                            target-x="490.8" target-y="609.8" />
                                        <path
                                            d="M 663.40 625.60 L 659.70 625.80 L 653.30 625.50 L 650.70 625.90 L 648.10 626.90 L 645.40 628.20 L 640.40 632.60 L 630.70 643.60 L 627.30 646.60 L 622.20 649.50 L 619.00 650.60 L 615.00 651.60 L 610.90 652.00 L 606.80 651.90 L 602.70 651.30 L 599.00 650.20 L 595.40 648.60 L 585.10 643.20 L 581.50 641.70 L 577.90 640.50 L 570.80 639.50 L 567.30 639.60 L 562.70 640.20 L 558.10 641.20 L 553.50 642.70 L 546.80 645.60 L 534.20 652.20 L 527.80 655.00 L 521.30 657.20 L 514.60 658.90 L 510.90 659.60 L 498.60 660.40 L 496.20 660.00 L 494.30 659.10 L 493.00 657.80 L 492.10 656.30 L 491.90 655.20 L 492.50 652.80 L 494.70 651.00 L 498.50 649.80 L 501.10 649.40 L 519.00 648.80 L 522.20 648.20 L 525.30 647.10 L 528.30 645.50 L 543.00 634.80 L 546.80 632.40 L 550.90 630.30 L 554.40 628.90 L 557.70 628.10 L 560.90 627.70 L 567.30 628.00 L 570.40 628.80 L 575.00 630.50 L 588.10 637.50 L 592.40 639.60 L 596.80 641.00 L 599.80 641.50 L 602.90 641.30 L 606.10 640.80 L 612.20 638.60 L 614.80 637.30 L 618.50 634.70 L 631.70 622.50 L 637.90 617.90 L 644.50 614.50 L 651.80 612.00 L 655.50 611.60 L 660.20 611.90 L 662.30 612.60 L 664.30 613.70 L 666.10 615.30 L 667.80 617.40 L 668.00 620.80 L 667.50 623.00 L 666.60 624.40 L 665.30 625.30 L 663.40 625.60 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="217"
                                            stroke="none" target-height="48.80005" target-width="176.1" target-x="491.9"
                                            target-y="611.6" />
                                        <path
                                            d="M 523.00 649.90 L 527.60 648.00 L 532.40 645.50 L 546.70 637.40 L 551.60 635.10 L 558.30 632.80 L 565.10 631.90 L 568.50 632.00 L 572.10 632.70 L 576.20 634.10 L 592.80 642.00 L 599.80 644.50 L 605.60 645.40 L 609.00 645.30 L 614.60 644.20 L 617.10 643.20 L 621.70 640.40 L 624.70 637.90 L 636.30 626.30 L 641.60 622.30 L 644.60 620.70 L 647.70 619.60 L 651.00 619.00 L 661.10 618.60 L 663.90 618.10 L 666.20 617.30 L 667.40 616.50 L 668.30 615.50 L 668.80 614.30 L 666.60 612.00 L 665.00 611.20 L 662.50 610.40 L 659.70 610.00 L 654.10 609.90 L 649.40 610.60 L 645.90 611.70 L 642.50 613.20 L 639.40 615.00 L 635.00 618.20 L 618.60 632.70 L 614.20 635.60 L 611.00 637.20 L 607.70 638.40 L 604.00 639.10 L 601.10 639.20 L 598.20 638.90 L 595.40 638.10 L 591.20 636.50 L 575.90 628.10 L 572.40 626.80 L 569.00 626.00 L 565.50 625.70 L 561.80 625.80 L 557.80 626.50 L 552.50 628.20 L 547.50 630.40 L 542.80 633.10 L 529.90 643.20 L 526.80 645.00 L 523.90 646.30 L 517.70 647.40 L 504.70 647.60 L 500.50 647.90 L 496.40 648.90 L 496.50 650.70 L 497.10 652.10 L 498.20 653.00 L 501.30 654.10 L 503.50 654.20 L 510.70 653.40 L 517.80 651.60 L 523.00 649.90 Z"
                                            fill="#54a8ba" fill-rule="nonzero" group-id="1,2,3" node-id="219"
                                            stroke="none" target-height="44.299988" target-width="172.4"
                                            target-x="496.4" target-y="609.9" />
                                        <path
                                            d="M 662.60 621.00 L 651.90 621.70 L 648.80 622.20 L 645.80 623.10 L 643.10 624.50 L 638.00 628.30 L 627.50 639.40 L 624.50 642.00 L 621.40 644.30 L 617.90 646.20 L 612.90 647.70 L 606.60 648.00 L 600.20 646.90 L 595.70 645.40 L 578.20 637.50 L 573.50 636.00 L 569.40 635.20 L 565.50 635.00 L 561.80 635.30 L 554.50 637.20 L 545.70 641.30 L 530.20 649.80 L 524.50 652.20 L 518.70 654.20 L 512.70 655.70 L 506.60 656.60 L 503.40 656.70 L 498.50 656.00 L 495.60 654.50 L 494.50 653.50 L 493.80 652.10 L 493.40 650.30 L 492.40 651.30 L 491.10 653.70 L 490.80 655.60 L 491.10 657.40 L 493.90 660.10 L 498.40 661.20 L 503.10 661.50 L 511.60 661.40 L 519.80 659.80 L 527.70 657.00 L 549.90 646.10 L 556.20 643.70 L 560.50 642.50 L 564.80 641.80 L 569.20 641.50 L 572.40 641.70 L 579.20 643.20 L 582.80 644.60 L 596.50 651.40 L 603.80 653.90 L 607.20 654.30 L 611.20 654.20 L 615.20 653.60 L 619.20 652.50 L 623.00 651.00 L 626.00 649.30 L 628.70 647.30 L 631.20 645.00 L 640.40 634.80 L 643.30 632.00 L 645.90 630.20 L 648.60 628.80 L 651.50 628.10 L 654.70 627.80 L 661.70 627.80 L 664.70 627.10 L 667.60 626.00 L 668.90 624.90 L 669.80 623.70 L 670.20 622.30 L 670.40 620.10 L 670.00 617.70 L 668.60 619.00 L 666.80 619.90 L 662.60 621.00 Z"
                                            fill="#009e8f" fill-rule="nonzero" group-id="1,2,3" node-id="221"
                                            stroke="none" target-height="43.799988" target-width="179.60004"
                                            target-x="490.8" target-y="617.7" />
                                        <path
                                            d="M 653.40 610.90 L 653.60 610.40 L 652.40 610.80 L 650.30 612.80 L 649.00 614.70 L 647.40 619.60 L 647.20 622.00 L 647.40 624.20 L 648.20 625.90 L 649.00 626.70 L 650.10 627.20 L 651.60 627.40 L 652.00 626.70 L 650.00 624.60 L 649.30 623.20 L 648.80 620.90 L 648.70 618.50 L 649.20 616.20 L 650.10 614.00 L 651.00 612.70 L 653.40 610.90 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="223"
                                            stroke="none" target-height="17" target-width="6.3999634" target-x="647.2"
                                            target-y="610.4" />
                                        <path
                                            d="M 644.20 630.50 L 640.70 627.60 L 639.80 626.10 L 638.80 623.40 L 638.30 620.60 L 638.50 618.70 L 640.10 614.90 L 638.50 617.00 L 637.60 619.40 L 637.30 622.00 L 637.50 624.70 L 638.50 627.80 L 640.60 630.10 L 642.00 630.80 L 643.80 631.00 L 644.20 630.50 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="225"
                                            stroke="none" target-height="16.099976" target-width="6.9000244"
                                            target-x="637.3" target-y="614.9" />
                                        <path
                                            d="M 635.90 638.60 L 633.10 636.60 L 632.00 635.50 L 631.00 633.90 L 630.10 631.70 L 629.30 628.30 L 629.20 625.10 L 629.60 623.90 L 630.50 622.90 L 629.80 623.00 L 628.80 624.70 L 628.40 628.50 L 628.40 630.20 L 629.70 634.60 L 631.60 638.00 L 632.70 639.10 L 634.50 639.70 L 635.50 639.40 L 635.90 638.60 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="227"
                                            stroke="none" target-height="16.799988" target-width="7.5" target-x="628.4"
                                            target-y="622.9" />
                                        <path
                                            d="M 627.70 647.10 L 626.60 646.80 L 624.70 645.00 L 621.60 639.30 L 620.60 635.10 L 621.00 631.90 L 620.50 632.10 L 619.80 634.50 L 619.80 636.90 L 621.00 641.70 L 622.30 644.40 L 624.40 647.20 L 625.30 647.90 L 627.00 648.30 L 627.60 647.70 L 627.70 647.10 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="229"
                                            stroke="none" target-height="16.399963" target-width="7.9000244"
                                            target-x="619.8" target-y="631.9" />
                                        <path
                                            d="M 614.00 652.40 L 611.20 647.40 L 610.40 644.50 L 610.20 641.20 L 610.60 639.60 L 611.40 638.10 L 610.00 639.70 L 609.40 641.60 L 609.10 643.70 L 609.70 648.40 L 611.00 651.60 L 611.90 652.90 L 613.00 653.50 L 613.70 653.10 L 614.00 652.40 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="231"
                                            stroke="none" target-height="15.400024" target-width="4.9000244"
                                            target-x="609.1" target-y="638.1" />
                                        <path
                                            d="M 600.70 649.10 L 600.20 646.70 L 600.30 644.20 L 601.90 639.50 L 600.50 641.20 L 599.30 644.80 L 599.00 647.40 L 599.50 650.20 L 600.10 651.40 L 601.00 652.30 L 601.50 652.10 L 601.70 651.60 L 600.70 649.10 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="233"
                                            stroke="none" target-height="12.799988" target-width="2.9000244"
                                            target-x="599" target-y="639.5" />
                                        <path
                                            d="M 592.60 638.00 L 591.40 638.40 L 589.80 640.60 L 589.00 642.60 L 588.70 644.70 L 588.80 646.90 L 589.50 646.70 L 589.90 646.20 L 590.20 641.90 L 591.50 639.10 L 592.00 638.50 L 592.40 638.50 L 592.60 638.00 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="235"
                                            stroke="none" target-height="8.900024" target-width="3.8999634"
                                            target-x="588.7" target-y="638" />
                                        <path
                                            d="M 583.60 633.20 L 582.70 633.60 L 580.80 636.50 L 580.30 637.80 L 580.10 641.40 L 580.70 641.90 L 581.40 640.90 L 581.50 637.00 L 582.60 634.50 L 583.70 633.30 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="237"
                                            stroke="none" target-height="8.700012" target-width="3.6000366"
                                            target-x="580.1" target-y="633.2" />
                                        <path
                                            d="M 574.90 629.40 L 573.40 631.00 L 572.30 632.80 L 571.00 637.10 L 571.00 639.60 L 571.30 640.70 L 572.20 641.60 L 572.90 640.60 L 572.40 638.60 L 572.50 634.80 L 573.00 632.90 L 575.00 629.60 L 574.90 629.40 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="239"
                                            stroke="none" target-height="12.199951" target-width="4" target-x="571"
                                            target-y="629.4" />
                                        <path
                                            d="M 565.20 626.10 L 564.00 626.50 L 563.00 627.30 L 561.80 629.80 L 561.00 634.40 L 561.30 638.80 L 562.40 641.00 L 563.20 641.40 L 564.10 640.70 L 562.40 635.10 L 562.10 630.70 L 563.00 627.90 L 565.20 626.10 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="241"
                                            stroke="none" target-height="15.300049" target-width="4.200012"
                                            target-x="561" target-y="626.1" />
                                        <path
                                            d="M 551.60 638.00 L 551.30 634.90 L 551.50 631.50 L 552.90 628.50 L 551.60 629.90 L 550.80 631.80 L 550.20 636.20 L 550.80 640.50 L 551.60 642.40 L 552.70 643.80 L 553.10 643.30 L 551.60 638.00 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="243"
                                            stroke="none" target-height="15.299988" target-width="2.8999634"
                                            target-x="550.2" target-y="628.5" />
                                        <path
                                            d="M 543.70 647.90 L 542.30 646.20 L 541.20 644.10 L 540.40 642.00 L 540.00 639.70 L 540.00 637.40 L 540.50 635.20 L 540.20 635.30 L 539.20 637.20 L 539.20 641.80 L 539.60 643.80 L 540.30 645.80 L 542.10 647.90 L 543.30 648.50 L 543.70 647.90 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="245"
                                            stroke="none" target-height="13.299988" target-width="4.5" target-x="539.2"
                                            target-y="635.2" />
                                        <path
                                            d="M 532.70 653.50 L 531.50 652.30 L 530.00 649.30 L 529.50 645.20 L 530.20 643.30 L 529.30 644.10 L 528.80 645.20 L 528.70 647.60 L 529.00 650.30 L 530.00 652.70 L 530.90 653.70 L 532.10 654.30 L 532.60 654.00 L 532.70 653.50 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="247"
                                            stroke="none" target-height="11" target-width="4" target-x="528.7"
                                            target-y="643.3" />
                                        <path
                                            d="M 519.10 658.50 L 517.70 657.50 L 516.70 655.80 L 516.10 653.90 L 515.80 651.90 L 516.00 650.00 L 516.60 648.20 L 515.40 649.90 L 514.90 651.60 L 514.90 653.40 L 515.30 655.80 L 516.50 658.20 L 517.30 659.00 L 518.40 659.50 L 519.00 659.10 L 519.10 658.50 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="249"
                                            stroke="none" target-height="11.299988" target-width="4.199951"
                                            target-x="514.9" target-y="648.2" />
                                        <path
                                            d="M 506.20 660.40 L 504.30 656.50 L 503.70 654.50 L 503.90 651.10 L 505.40 648.30 L 503.70 649.60 L 502.90 651.30 L 502.50 654.30 L 503.10 657.80 L 503.60 659.30 L 504.50 660.60 L 505.50 661.30 L 506.00 661.00 L 506.20 660.40 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="251"
                                            stroke="none" target-height="13" target-width="3.7000122" target-x="502.5"
                                            target-y="648.3" />
                                        <path
                                            d="M 661.60 623.10 L 662.40 624.00 L 663.50 624.40 L 664.80 624.40 L 665.90 624.00 L 666.80 623.20 L 667.00 621.90 L 666.20 622.00 L 665.90 623.10 L 664.60 623.90 L 663.00 623.60 L 662.50 622.90 L 661.60 623.10 Z"
                                            fill="#483065" fill-rule="nonzero" group-id="1,2,3" node-id="253"
                                            stroke="none" target-height="2.5" target-width="5.4000244" target-x="661.6"
                                            target-y="621.9" />
                                        <path
                                            d="M 663.10 615.70 L 663.10 617.50 L 662.60 619.00 L 662.00 619.80 L 660.20 620.80 L 658.30 620.60 L 657.40 620.00 L 656.30 618.80 L 655.70 617.20 L 655.60 615.40 L 656.20 613.90 L 656.80 613.00 L 658.50 612.10 L 660.50 612.30 L 661.40 612.80 L 662.40 614.00 L 663.10 615.70 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="255"
                                            stroke="none" target-height="8.700012" target-width="7.5" target-x="655.6"
                                            target-y="612.1" />
                                        <path
                                            d="M 662.60 615.70 L 662.70 616.90 L 662.00 619.00 L 660.50 620.50 L 659.10 620.60 L 657.20 619.20 L 656.20 617.10 L 656.00 616.00 L 656.70 613.80 L 657.40 612.90 L 658.90 612.10 L 660.60 612.70 L 661.50 613.60 L 662.70 615.90 L 663.60 615.70 L 662.90 614.10 L 661.90 612.80 L 660.00 611.90 L 657.60 612.20 L 655.90 613.70 L 655.30 615.30 L 655.20 617.20 L 655.80 618.80 L 656.90 620.00 L 658.30 620.80 L 660.00 621.00 L 662.20 620.00 L 662.90 619.20 L 663.60 617.60 L 663.60 615.70 L 662.60 615.70 Z"
                                            fill="#8055bf" fill-rule="nonzero" group-id="1,2,3" node-id="257"
                                            stroke="none" target-height="9.099976" target-width="8.399963"
                                            target-x="655.2" target-y="611.9" />
                                        <g node-id="589">
                                            <path
                                                d="M 658.50 612.10 L 657.20 612.70 L 656.20 613.90 L 655.60 615.40 L 655.70 617.20 L 656.30 618.80 L 657.40 620.00 L 658.30 620.50 L 660.20 620.80 L 662.00 619.80 L 662.60 619.00 L 663.10 617.50 L 663.10 615.70 L 662.40 614.00 L 661.40 612.80 L 660.50 612.30 L 658.50 612.10 Z M 659.80 619.40 L 658.70 619.60 L 656.70 619.10 L 655.30 617.40 L 655.10 615.30 L 655.40 614.30 L 657.00 612.80 L 659.10 612.50 L 660.20 612.80 L 661.70 614.30 L 662.20 616.60 L 661.80 617.80 L 661.00 618.80 L 659.80 619.40 Z"
                                                fill="#c7c6cc" fill-rule="nonzero" group-id="1,2,3,5" node-id="261"
                                                stroke="none" target-height="8.700012" target-width="8" target-x="655.1"
                                                target-y="612.1" />
                                        </g>
                                        <path
                                            d="M 661.40 616.00 L 660.90 617.30 L 659.70 617.90 L 658.40 617.50 L 657.70 616.30 L 658.00 615.00 L 659.10 614.30 L 660.60 614.70 L 661.40 616.00 Z"
                                            fill="#332051" fill-rule="nonzero" group-id="1,2,3" node-id="264"
                                            stroke="none" target-height="3.6000366" target-width="3.7000122"
                                            target-x="657.7" target-y="614.3" />
                                        <path
                                            d="M 660.80 616.10 L 660.50 617.00 L 659.60 617.40 L 658.70 617.10 L 658.30 616.30 L 658.50 615.40 L 659.30 614.90 L 660.30 615.20 L 660.80 616.10 Z"
                                            fill="#1a102b" fill-rule="nonzero" group-id="1,2,3" node-id="266"
                                            stroke="none" target-height="2.5" target-width="2.5" target-x="658.3"
                                            target-y="614.9" />
                                        <path
                                            d="M 660.70 615.00 L 660.90 615.60 L 660.60 616.00 L 660.20 616.00 L 659.70 614.90 L 659.90 614.60 L 660.30 614.60 L 660.70 615.00 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="268"
                                            stroke="none" target-height="1.4000244" target-width="1.2000122"
                                            target-x="659.7" target-y="614.6" />
                                        <path
                                            d="M 672.60 613.70 L 672.60 615.70 L 672.10 617.30 L 671.40 618.30 L 669.60 619.30 L 667.50 619.00 L 666.50 618.40 L 665.40 617.10 L 664.70 615.30 L 664.60 613.40 L 665.20 611.70 L 665.80 610.80 L 667.70 609.70 L 669.80 610.00 L 670.80 610.60 L 671.90 611.90 L 672.60 613.70 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="270"
                                            stroke="none" target-height="9.599976" target-width="8" target-x="664.6"
                                            target-y="609.7" />
                                        <path
                                            d="M 672.10 613.80 L 672.20 615.20 L 671.40 617.70 L 670.50 618.70 L 669.60 619.20 L 668.10 619.10 L 666.20 617.40 L 665.20 615.20 L 665.10 613.80 L 665.90 611.30 L 667.70 609.80 L 669.20 609.90 L 671.10 611.60 L 672.30 613.90 L 673.10 613.60 L 672.30 611.80 L 671.20 610.40 L 669.00 609.50 L 667.70 609.50 L 666.40 610.00 L 664.80 611.70 L 664.30 613.40 L 664.30 615.40 L 664.60 616.60 L 666.10 618.60 L 667.20 619.20 L 669.70 619.50 L 671.90 618.30 L 672.50 617.30 L 673.10 615.50 L 673.10 613.60 L 672.50 613.50 L 672.10 613.80 Z"
                                            fill="#8055bf" fill-rule="nonzero" group-id="1,2,3" node-id="272"
                                            stroke="none" target-height="10" target-width="8.799988" target-x="664.3"
                                            target-y="609.5" />
                                        <g node-id="590">
                                            <path
                                                d="M 667.70 609.70 L 665.90 610.70 L 665.20 611.60 L 664.70 613.30 L 664.70 615.30 L 665.40 617.10 L 666.50 618.40 L 668.50 619.30 L 669.60 619.30 L 671.40 618.20 L 672.10 617.30 L 672.60 615.70 L 672.60 613.70 L 672.20 612.40 L 670.80 610.50 L 669.80 609.90 L 667.70 609.70 Z M 669.10 617.70 L 667.70 617.60 L 666.60 617.00 L 665.60 615.90 L 665.10 614.40 L 665.00 612.80 L 665.40 611.50 L 666.30 610.40 L 667.50 609.90 L 668.80 609.90 L 670.00 610.60 L 670.90 611.60 L 671.50 613.10 L 671.50 614.70 L 671.10 616.10 L 670.20 617.20 L 669.10 617.70 Z"
                                                fill="#c7c6cc" fill-rule="nonzero" group-id="1,2,3,6" node-id="276"
                                                stroke="none" target-height="9.599976" target-width="7.8999634"
                                                target-x="664.7" target-y="609.7" />
                                        </g>
                                        <path
                                            d="M 670.50 614.20 L 670.00 615.50 L 668.80 616.10 L 667.50 615.70 L 666.80 614.60 L 667.10 613.20 L 668.30 612.50 L 669.70 612.90 L 670.50 614.20 Z"
                                            fill="#332051" fill-rule="nonzero" group-id="1,2,3" node-id="279"
                                            stroke="none" target-height="3.5999756" target-width="3.7000122"
                                            target-x="666.8" target-y="612.5" />
                                        <path
                                            d="M 669.90 614.30 L 669.60 615.20 L 668.80 615.60 L 667.90 615.40 L 667.40 614.60 L 667.60 613.70 L 668.40 613.20 L 669.40 613.40 L 669.90 614.30 Z"
                                            fill="#1a102b" fill-rule="nonzero" group-id="1,2,3" node-id="281"
                                            stroke="none" target-height="2.3999634" target-width="2.5" target-x="667.4"
                                            target-y="613.2" />
                                        <path
                                            d="M 669.90 613.20 L 670.00 613.80 L 669.80 614.20 L 669.40 614.20 L 668.80 613.20 L 669.40 612.80 L 669.90 613.20 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="283"
                                            stroke="none" target-height="1.4000244" target-width="1.2000122"
                                            target-x="668.8" target-y="612.8" />
                                        <path
                                            d="M 223.60 573.10 L 206.50 561.70 L 200.00 522.00 L 198.40 519.20 L 196.60 517.60 L 194.50 516.40 L 191.30 515.80 L 188.00 516.40 L 185.20 517.90 L 183.50 519.70 L 182.40 521.80 L 181.80 525.00 L 187.80 563.30 L 185.80 565.60 L 175.10 583.60 L 174.10 585.80 L 173.70 588.00 L 173.90 590.30 L 174.70 592.50 L 176.10 594.40 L 177.90 595.80 L 180.00 596.80 L 182.40 597.10 L 184.70 596.80 L 186.80 595.90 L 188.60 594.50 L 193.20 587.40 L 193.80 591.30 L 194.50 593.50 L 195.70 595.40 L 197.40 596.80 L 199.30 597.90 L 201.50 598.40 L 203.80 598.30 L 206.00 597.60 L 207.90 596.50 L 209.40 594.90 L 210.50 593.00 L 211.10 590.80 L 211.10 588.50 L 210.60 585.60 L 213.90 587.80 L 216.00 588.70 L 218.20 589.10 L 220.40 588.90 L 222.50 588.20 L 224.40 587.00 L 225.90 585.20 L 226.90 583.10 L 227.30 580.90 L 227.20 578.70 L 226.50 576.60 L 225.40 574.80 L 223.60 573.20 Z"
                                            fill="#f59a00" fill-rule="nonzero" group-id="1,2,3" node-id="285"
                                            stroke="none" target-height="82.60004" target-width="53.600006"
                                            target-x="173.7" target-y="515.8" />
                                        <path
                                            d="M 334.10 555.30 L 317.00 543.90 L 310.40 504.20 L 308.80 501.40 L 307.10 499.70 L 305.00 498.60 L 301.80 497.90 L 298.40 498.50 L 295.60 500.10 L 294.00 501.80 L 292.80 503.90 L 292.20 507.10 L 298.30 545.50 L 296.30 547.80 L 285.50 565.80 L 284.60 568.00 L 284.30 570.20 L 284.60 572.40 L 285.40 574.50 L 286.70 576.30 L 288.60 577.80 L 290.70 578.70 L 293.00 579.00 L 295.20 578.80 L 297.30 578.00 L 299.10 576.60 L 300.60 574.80 L 303.70 569.60 L 304.30 573.40 L 305.00 575.60 L 306.30 577.50 L 307.90 579.00 L 309.80 580.00 L 312.00 580.50 L 314.30 580.40 L 316.50 579.80 L 318.40 578.60 L 319.90 577.00 L 321.00 575.10 L 321.60 573.00 L 321.60 570.70 L 321.10 567.70 L 326.60 570.90 L 328.80 571.30 L 331.00 571.20 L 333.10 570.40 L 335.00 569.20 L 336.60 567.40 L 337.60 565.30 L 338.00 563.10 L 337.90 560.90 L 337.20 558.70 L 335.90 556.90 L 334.20 555.30 Z"
                                            fill="#f59a00" fill-rule="nonzero" group-id="1,2,3" node-id="287"
                                            stroke="none" target-height="82.600006" target-width="53.700012"
                                            target-x="284.3" target-y="497.9" />
                                        <path
                                            d="M 583.20 279.60 L 584.60 278.80 L 585.30 277.90 L 585.40 276.90 L 585.00 275.60 L 583.80 274.10 L 581.60 272.10 L 578.00 269.60 L 569.60 265.10 L 554.70 258.90 L 543.80 255.20 L 532.30 251.80 L 519.50 248.70 L 501.70 245.40 L 492.30 244.00 L 486.30 243.60 L 480.00 243.50 L 473.50 243.90 L 466.70 244.80 L 460.00 246.10 L 446.30 250.00 L 439.30 252.60 L 425.70 258.90 L 412.30 266.70 L 405.80 271.10 L 393.50 280.90 L 387.60 286.40 L 382.00 292.10 L 376.80 298.10 L 371.90 304.40 L 367.40 311.00 L 363.20 317.80 L 359.60 324.90 L 356.40 332.20 L 353.60 339.80 L 351.50 347.60 L 349.90 355.50 L 348.80 363.70 L 348.40 372.20 L 348.70 379.70 L 349.40 386.40 L 350.40 392.50 L 351.90 397.90 L 353.90 403.40 L 356.10 408.20 L 358.60 412.60 L 361.40 416.60 L 367.90 423.60 L 374.90 429.20 L 380.10 432.40 L 385.30 435.00 L 395.90 438.90 L 406.10 441.20 L 413.50 442.30 L 425.50 443.00 L 433.00 442.70 L 448.40 439.10 L 456.60 436.60 L 468.80 432.40 L 519.30 412.70 L 531.70 408.30 L 540.00 405.60 L 555.90 401.60 L 563.70 400.10 L 577.20 398.60 L 586.50 398.30 L 594.40 398.40 L 613.90 399.60 L 618.70 399.50 L 624.60 398.50 L 628.00 396.90 L 628.70 395.20 L 628.50 394.20 L 626.70 391.00 L 621.60 385.90 L 617.50 382.50 L 608.00 375.60 L 598.00 369.00 L 579.80 358.10 L 589.70 359.80 L 599.40 361.00 L 610.10 361.60 L 615.30 361.50 L 622.90 360.50 L 625.30 359.70 L 627.20 358.80 L 628.10 357.90 L 628.50 357.00 L 628.40 356.00 L 626.70 353.10 L 622.10 348.90 L 617.70 345.70 L 612.20 342.20 L 600.50 335.60 L 577.60 324.00 L 592.20 321.50 L 602.30 318.60 L 607.10 316.60 L 610.20 315.10 L 615.20 311.50 L 616.00 310.50 L 616.30 309.60 L 616.10 308.70 L 615.30 307.70 L 611.90 305.30 L 608.70 303.90 L 603.80 302.10 L 590.70 298.50 L 568.50 294.00 L 554.90 291.80 L 538.70 289.80 L 564.60 284.60 L 574.90 282.20 L 583.20 279.60 Z"
                                            fill="#1794a5" fill-rule="nonzero" group-id="1,2,3" node-id="289"
                                            stroke="none" target-height="199.5" target-width="280.30002"
                                            target-x="348.4" target-y="243.5" />
                                        <path
                                            d="M 66.50 461.50 L 60.80 461.60 L 29.10 458.10 L 19.40 457.70 L 15.60 458.00 L 13.30 458.60 L 12.10 459.80 L 12.50 461.50 L 15.60 465.20 L 24.70 473.10 L 32.90 479.00 L 45.50 486.90 L 61.40 495.30 L 70.30 499.40 L 80.20 503.50 L 90.20 507.20 L 100.90 510.70 L 112.60 514.00 L 124.40 516.80 L 136.90 519.10 L 150.20 521.00 L 161.70 522.20 L 187.20 523.90 L 200.30 524.20 L 214.00 524.10 L 228.10 523.50 L 242.20 522.50 L 256.50 520.90 L 270.90 518.70 L 281.70 516.60 L 292.40 514.10 L 303.10 511.20 L 313.70 507.90 L 324.10 504.10 L 334.30 499.80 L 344.40 495.10 L 354.20 489.80 L 361.90 485.20 L 369.30 480.20 L 376.60 474.90 L 383.70 469.20 L 390.60 463.00 L 397.10 456.60 L 403.40 449.80 L 409.40 442.40 L 415.20 434.60 L 420.70 426.20 L 426.10 417.10 L 431.00 407.60 L 435.40 397.90 L 439.30 387.90 L 442.80 377.60 L 445.70 367.30 L 448.10 356.80 L 450.10 346.10 L 451.50 335.40 L 452.30 324.70 L 452.70 314.10 L 452.50 303.40 L 451.70 292.70 L 450.40 282.30 L 448.50 272.00 L 446.10 261.80 L 443.70 253.80 L 440.90 246.00 L 437.80 238.40 L 434.30 231.00 L 430.40 223.70 L 426.20 216.70 L 421.50 210.00 L 416.40 203.50 L 411.00 197.30 L 405.10 191.40 L 398.90 185.90 L 392.30 180.70 L 385.20 175.80 L 377.60 171.20 L 369.60 167.00 L 361.50 163.40 L 352.80 160.20 L 343.60 157.30 L 333.80 154.90 L 323.50 152.90 L 311.10 151.60 L 304.90 151.60 L 298.70 152.30 L 283.00 155.60 L 270.90 159.30 L 257.80 164.50 L 251.30 167.70 L 244.70 171.40 L 238.30 175.50 L 232.30 180.20 L 226.40 185.40 L 222.30 189.60 L 218.50 194.20 L 215.00 199.20 L 211.70 204.60 L 209.10 209.80 L 206.50 216.00 L 203.80 223.50 L 200.20 235.20 L 184.50 294.30 L 179.80 310.60 L 174.60 326.90 L 168.80 343.00 L 164.60 353.70 L 159.90 363.90 L 155.00 373.90 L 146.60 388.10 L 137.30 401.10 L 132.20 407.10 L 126.80 412.70 L 121.10 417.90 L 115.10 422.70 L 110.10 426.10 L 104.80 429.30 L 99.30 432.00 L 93.50 434.50 L 87.40 436.60 L 81.30 438.30 L 74.90 439.60 L 68.20 440.50 L 61.00 441.00 L 53.40 441.00 L 62.70 450.50 L 66.40 455.30 L 67.60 457.60 L 68.10 459.30 L 68.00 460.30 L 67.50 461.00 L 66.50 461.50 Z"
                                            fill="#009e8f" fill-rule="nonzero" group-id="1,2,3" node-id="291"
                                            stroke="none" target-height="372.6" target-width="440.6" target-x="12.1"
                                            target-y="151.6" />
                                        <g node-id="591">
                                            <g clip-path="url(#clip-path)" group-id="1,2,3,7" node-id="293">
                                                <path
                                                    d="M 509.90 364.80 L 507.50 356.10 L 504.60 347.50 L 501.10 339.20 L 497.00 331.10 L 493.10 324.50 L 489.00 318.20 L 484.60 312.20 L 479.90 306.50 L 474.90 301.20 L 468.30 294.90 L 461.30 289.10 L 454.00 283.80 L 446.40 279.00 L 438.50 274.70 L 430.40 270.90 L 422.00 267.70 L 413.40 265.00 L 404.70 262.90 L 395.90 261.30 L 386.90 260.20 L 377.80 259.80 L 368.70 259.90 L 359.50 260.60 L 350.40 261.90 L 341.20 263.80 L 332.20 266.30 L 323.30 269.40 L 314.40 273.10 L 305.60 277.60 L 297.10 282.60 L 289.10 288.00 L 281.70 293.80 L 274.70 300.00 L 268.10 306.70 L 262.10 313.70 L 256.60 321.00 L 251.60 328.60 L 247.00 336.50 L 243.00 344.60 L 239.60 352.90 L 236.60 361.40 L 234.30 370.10 L 232.40 378.90 L 231.20 387.70 L 230.50 396.70 L 230.40 405.70 L 231.00 414.70 L 232.10 423.70 L 233.80 432.70 L 235.60 439.80 L 237.80 446.80 L 240.50 453.70 L 243.50 460.60 L 247.00 467.50 L 251.60 475.30 L 256.70 482.80 L 262.40 489.90 L 268.50 496.70 L 278.30 497.90 L 288.10 498.60 L 297.70 498.70 L 307.20 498.30 L 316.70 497.40 L 326.20 496.00 L 335.60 494.30 L 344.90 492.00 L 354.20 489.40 L 363.40 486.30 L 374.70 482.00 L 386.00 477.10 L 397.20 471.70 L 419.00 459.80 L 429.40 453.40 L 439.50 446.70 L 449.30 439.40 L 458.60 431.80 L 467.60 423.70 L 474.00 417.30 L 480.10 410.70 L 485.90 403.80 L 491.40 396.60 L 496.60 389.10 L 501.40 381.30 L 505.80 373.30 L 509.90 364.80 Z"
                                                    fill="#54a8ba" fill-rule="nonzero" group-id="1,2,3,7" node-id="295"
                                                    stroke="none" target-height="238.90002" target-width="279.5"
                                                    target-x="230.4" target-y="259.8" />
                                                <path
                                                    d="M 509.90 364.80 L 507.50 356.10 L 504.60 347.50 L 501.10 339.20 L 497.00 331.10 L 493.10 324.50 L 489.00 318.20 L 484.60 312.20 L 479.90 306.50 L 474.90 301.20 L 468.30 294.90 L 461.30 289.10 L 454.00 283.80 L 446.40 279.00 L 438.50 274.70 L 430.40 270.90 L 422.00 267.70 L 413.40 265.00 L 404.70 262.90 L 395.90 261.30 L 386.90 260.20 L 377.80 259.80 L 368.70 259.90 L 359.50 260.60 L 350.40 261.90 L 341.20 263.80 L 332.20 266.30 L 323.30 269.40 L 314.40 273.10 L 305.60 277.60 L 297.10 282.60 L 289.10 288.00 L 281.70 293.80 L 274.70 300.00 L 268.10 306.70 L 262.10 313.70 L 256.60 321.00 L 251.60 328.60 L 247.00 336.50 L 243.00 344.60 L 239.60 352.90 L 236.60 361.40 L 234.30 370.10 L 232.40 378.90 L 231.20 387.70 L 230.50 396.70 L 230.40 405.70 L 231.00 414.70 L 232.10 423.70 L 233.80 432.70 L 235.60 439.80 L 237.80 446.80 L 240.50 453.70 L 243.50 460.60 L 247.00 467.50 L 251.60 475.30 L 256.70 482.80 L 262.40 489.90 L 268.50 496.70 L 278.30 497.90 L 288.10 498.60 L 297.70 498.70 L 307.20 498.30 L 316.70 497.40 L 326.20 496.00 L 335.60 494.30 L 344.90 492.00 L 354.20 489.40 L 363.40 486.30 L 374.70 482.00 L 386.00 477.10 L 397.20 471.70 L 419.00 459.80 L 429.40 453.40 L 439.50 446.70 L 449.30 439.40 L 458.60 431.80 L 467.60 423.70 L 474.00 417.30 L 480.10 410.70 L 485.90 403.80 L 491.40 396.60 L 496.60 389.10 L 501.40 381.30 L 505.80 373.30 L 509.90 364.80 Z"
                                                    fill="#a5d7d3" fill-opacity="0.8" fill-rule="nonzero"
                                                    group-id="1,2,3,7,11" node-id="299" stroke="none"
                                                    target-height="238.90002" target-width="279.5" target-x="230.4"
                                                    target-y="259.8" />
                                                <g clip-path="url(#clip-path-2)" group-id="1,2,3,7,12" node-id="302">
                                                    <path
                                                        d="M 270.90 355.40 L 245.60 369.20 L 265.60 375.10 L 270.90 355.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="304" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="245.6" target-y="355.4" />
                                                    <path
                                                        d="M 299.10 340.00 L 273.90 353.80 L 293.80 359.60 L 299.10 340.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="306" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="273.9" target-y="340" />
                                                    <path
                                                        d="M 327.40 324.60 L 302.10 338.40 L 322.10 344.30 L 327.40 324.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="308" stroke="none" target-height="19.699982"
                                                        target-width="25.299988" target-x="302.1" target-y="324.6" />
                                                    <path
                                                        d="M 355.60 309.20 L 330.40 323.00 L 350.30 328.80 L 355.60 309.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="310" stroke="none" target-height="19.599976"
                                                        target-width="25.200012" target-x="330.4" target-y="309.2" />
                                                    <path
                                                        d="M 383.90 293.80 L 358.60 307.60 L 378.60 313.40 L 383.90 293.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="312" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="358.6" target-y="293.8" />
                                                    <path
                                                        d="M 412.10 278.40 L 386.90 292.20 L 406.80 298.00 L 412.10 278.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="314" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="386.9" target-y="278.4" />
                                                    <path
                                                        d="M 440.40 263.00 L 415.10 276.80 L 435.10 282.60 L 440.40 263.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="316" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="415.1" target-y="263" />
                                                    <path
                                                        d="M 242.60 370.80 L 217.40 384.60 L 237.30 390.50 L 242.60 370.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="318" stroke="none" target-height="19.700012"
                                                        target-width="25.200012" target-x="217.4" target-y="370.8" />
                                                    <path
                                                        d="M 275.80 330.80 L 250.50 344.60 L 270.50 350.50 L 275.80 330.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="320" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="250.5" target-y="330.8" />
                                                    <path
                                                        d="M 304.10 315.40 L 278.80 329.20 L 298.70 335.10 L 304.10 315.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="322" stroke="none" target-height="19.700012"
                                                        target-width="25.300018" target-x="278.8" target-y="315.4" />
                                                    <path
                                                        d="M 332.30 300.00 L 307.00 313.80 L 327.00 319.70 L 332.30 300.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="324" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="307" target-y="300" />
                                                    <path
                                                        d="M 360.60 284.60 L 335.30 298.40 L 355.20 304.20 L 360.60 284.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="326" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="335.3" target-y="284.6" />
                                                    <path
                                                        d="M 388.80 269.20 L 363.50 283.00 L 383.50 288.80 L 388.80 269.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="328" stroke="none" target-height="19.599976"
                                                        target-width="25.299988" target-x="363.5" target-y="269.2" />
                                                    <path
                                                        d="M 417.00 253.80 L 391.80 267.60 L 411.70 273.40 L 417.00 253.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="330" stroke="none" target-height="19.59999"
                                                        target-width="25.200012" target-x="391.8" target-y="253.8" />
                                                    <path
                                                        d="M 247.60 346.20 L 222.30 360.00 L 242.20 365.90 L 247.60 346.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="332" stroke="none" target-height="19.699982"
                                                        target-width="25.300003" target-x="222.3" target-y="346.2" />
                                                    <path
                                                        d="M 473.50 223.00 L 448.30 236.80 L 468.20 242.60 L 473.50 223.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="334" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="448.3" target-y="223" />
                                                    <path
                                                        d="M 229.10 312.40 L 203.80 326.20 L 223.80 332.10 L 229.10 312.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="336" stroke="none" target-height="19.700012"
                                                        target-width="25.300003" target-x="203.8" target-y="312.4" />
                                                    <path
                                                        d="M 257.40 297.00 L 232.10 310.80 L 252.10 316.70 L 257.40 297.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="338" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="232.1" target-y="297" />
                                                    <path
                                                        d="M 285.60 281.60 L 260.30 295.40 L 280.30 301.30 L 285.60 281.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="340" stroke="none" target-height="19.699982"
                                                        target-width="25.300018" target-x="260.3" target-y="281.6" />
                                                    <path
                                                        d="M 313.90 266.20 L 288.60 280.00 L 308.60 285.90 L 313.90 266.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="342" stroke="none" target-height="19.699982"
                                                        target-width="25.299988" target-x="288.6" target-y="266.2" />
                                                    <path
                                                        d="M 342.10 250.80 L 316.80 264.60 L 336.80 270.50 L 342.10 250.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="344" stroke="none" target-height="19.699997"
                                                        target-width="25.300018" target-x="316.8" target-y="250.8" />
                                                    <path
                                                        d="M 370.40 235.40 L 345.10 249.20 L 365.10 255.00 L 370.40 235.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="346" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="345.1" target-y="235.4" />
                                                    <path
                                                        d="M 398.60 220.00 L 373.30 233.80 L 393.30 239.60 L 398.60 220.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="348" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="373.3" target-y="220" />
                                                    <path
                                                        d="M 200.90 327.80 L 175.60 341.60 L 195.60 347.50 L 200.90 327.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="350" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="175.6" target-y="327.8" />
                                                    <path
                                                        d="M 455.10 189.20 L 429.80 203.00 L 449.80 208.80 L 455.10 189.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="352" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="429.8" target-y="189.2" />
                                                    <path
                                                        d="M 426.90 204.60 L 401.60 218.40 L 421.60 224.20 L 426.90 204.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="354" stroke="none" target-height="19.59999"
                                                        target-width="25.299988" target-x="401.6" target-y="204.6" />
                                                    <path
                                                        d="M 252.50 321.60 L 227.20 335.40 L 247.20 341.30 L 252.50 321.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="356" stroke="none" target-height="19.699982"
                                                        target-width="25.300003" target-x="227.2" target-y="321.6" />
                                                    <path
                                                        d="M 280.70 306.20 L 255.40 320.00 L 275.40 325.90 L 280.70 306.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="358" stroke="none" target-height="19.699982"
                                                        target-width="25.300018" target-x="255.4" target-y="306.2" />
                                                    <path
                                                        d="M 309.00 290.80 L 283.70 304.60 L 303.60 310.50 L 309.00 290.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="360" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="283.7" target-y="290.8" />
                                                    <path
                                                        d="M 337.20 275.40 L 311.90 289.20 L 331.90 295.10 L 337.20 275.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="362" stroke="none" target-height="19.700012"
                                                        target-width="25.300018" target-x="311.9" target-y="275.4" />
                                                    <path
                                                        d="M 365.50 260.00 L 340.20 273.80 L 360.10 279.60 L 365.50 260.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="364" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="340.2" target-y="260" />
                                                    <path
                                                        d="M 393.70 244.60 L 368.40 258.40 L 388.40 264.20 L 393.70 244.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="366" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="368.4" target-y="244.6" />
                                                    <path
                                                        d="M 224.20 337.00 L 198.90 350.80 L 218.90 356.70 L 224.20 337.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="368" stroke="none" target-height="19.700012"
                                                        target-width="25.300003" target-x="198.9" target-y="337" />
                                                    <path
                                                        d="M 478.50 198.40 L 453.20 212.10 L 473.10 218.00 L 478.50 198.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="370" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="453.2" target-y="198.4" />
                                                    <path
                                                        d="M 450.20 213.80 L 424.90 227.60 L 444.90 233.40 L 450.20 213.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="372" stroke="none" target-height="19.59999"
                                                        target-width="25.300018" target-x="424.9" target-y="213.8" />
                                                    <path
                                                        d="M 306.30 423.80 L 281.00 437.60 L 301.00 443.50 L 306.30 423.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="374" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="281" target-y="423.8" />
                                                    <path
                                                        d="M 334.50 408.40 L 309.20 422.20 L 329.20 428.10 L 334.50 408.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="376" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="309.2" target-y="408.4" />
                                                    <path
                                                        d="M 362.80 393.00 L 337.50 406.80 L 357.50 412.60 L 362.80 393.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="378" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="337.5" target-y="393" />
                                                    <path
                                                        d="M 391.00 377.60 L 365.70 391.40 L 385.70 397.20 L 391.00 377.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="380" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="365.7" target-y="377.6" />
                                                    <path
                                                        d="M 419.30 362.20 L 394.00 376.00 L 413.90 381.80 L 419.30 362.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="382" stroke="none" target-height="19.599976"
                                                        target-width="25.299988" target-x="394" target-y="362.2" />
                                                    <path
                                                        d="M 447.50 346.80 L 422.20 360.60 L 442.20 366.40 L 447.50 346.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="384" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="422.2" target-y="346.8" />
                                                    <path
                                                        d="M 475.80 331.40 L 450.50 345.20 L 470.40 351.00 L 475.80 331.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="386" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="450.5" target-y="331.4" />
                                                    <path
                                                        d="M 278.00 439.20 L 252.70 453.00 L 272.70 458.90 L 278.00 439.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="388" stroke="none" target-height="19.699982"
                                                        target-width="25.300003" target-x="252.7" target-y="439.2" />
                                                    <path
                                                        d="M 249.80 454.60 L 224.50 468.40 L 244.50 474.30 L 249.80 454.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="390" stroke="none" target-height="19.699982"
                                                        target-width="25.300003" target-x="224.5" target-y="454.6" />
                                                    <path
                                                        d="M 532.30 300.60 L 507.00 314.30 L 526.90 320.20 L 532.30 300.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="392" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="507" target-y="300.6" />
                                                    <path
                                                        d="M 504.00 316.00 L 478.70 329.80 L 498.70 335.60 L 504.00 316.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="394" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="478.7" target-y="316" />
                                                    <path
                                                        d="M 227.90 444.60 L 202.60 458.40 L 222.60 464.30 L 227.90 444.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="396" stroke="none" target-height="19.699982"
                                                        target-width="25.299988" target-x="202.6" target-y="444.6" />
                                                    <path
                                                        d="M 311.20 399.20 L 285.90 413.00 L 305.90 418.90 L 311.20 399.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="398" stroke="none" target-height="19.699982"
                                                        target-width="25.300018" target-x="285.9" target-y="399.2" />
                                                    <path
                                                        d="M 339.40 383.80 L 314.10 397.60 L 334.10 403.50 L 339.40 383.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="400" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="314.1" target-y="383.8" />
                                                    <path
                                                        d="M 367.70 368.40 L 342.40 382.20 L 362.40 388.00 L 367.70 368.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="402" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="342.4" target-y="368.4" />
                                                    <path
                                                        d="M 395.90 353.00 L 370.60 366.80 L 390.60 372.60 L 395.90 353.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="404" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="370.6" target-y="353" />
                                                    <path
                                                        d="M 424.20 337.60 L 398.90 351.40 L 418.90 357.20 L 424.20 337.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="406" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="398.9" target-y="337.6" />
                                                    <path
                                                        d="M 452.40 322.20 L 427.10 336.00 L 447.10 341.80 L 452.40 322.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="408" stroke="none" target-height="19.599976"
                                                        target-width="25.299988" target-x="427.1" target-y="322.2" />
                                                    <path
                                                        d="M 480.70 306.80 L 455.40 320.60 L 475.40 326.40 L 480.70 306.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="410" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="455.4" target-y="306.8" />
                                                    <path
                                                        d="M 282.90 414.60 L 257.70 428.40 L 277.60 434.30 L 282.90 414.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="412" stroke="none" target-height="19.699982"
                                                        target-width="25.199982" target-x="257.7" target-y="414.6" />
                                                    <path
                                                        d="M 254.70 430.00 L 229.40 443.80 L 249.40 449.70 L 254.70 430.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="414" stroke="none" target-height="19.700012"
                                                        target-width="25.300003" target-x="229.4" target-y="430" />
                                                    <path
                                                        d="M 508.90 291.40 L 483.60 305.10 L 503.60 311.00 L 508.90 291.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="416" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="483.6" target-y="291.4" />
                                                    <path
                                                        d="M 208.00 411.60 L 182.70 425.40 L 202.70 431.30 L 208.00 411.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="418" stroke="none" target-height="19.699982"
                                                        target-width="25.300003" target-x="182.7" target-y="411.6" />
                                                    <path
                                                        d="M 264.50 380.80 L 239.20 394.60 L 259.20 400.50 L 264.50 380.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="420" stroke="none" target-height="19.700012"
                                                        target-width="25.300003" target-x="239.2" target-y="380.8" />
                                                    <path
                                                        d="M 292.80 365.40 L 267.50 379.20 L 287.40 385.10 L 292.80 365.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="422" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="267.5" target-y="365.4" />
                                                    <path
                                                        d="M 321.00 350.00 L 295.70 363.80 L 315.70 369.70 L 321.00 350.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="424" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="295.7" target-y="350" />
                                                    <path
                                                        d="M 349.20 334.60 L 324.00 348.40 L 343.90 354.30 L 349.20 334.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="426" stroke="none" target-height="19.699982"
                                                        target-width="25.200012" target-x="324" target-y="334.6" />
                                                    <path
                                                        d="M 377.50 319.20 L 352.20 333.00 L 372.20 338.90 L 377.50 319.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="428" stroke="none" target-height="19.699982"
                                                        target-width="25.299988" target-x="352.2" target-y="319.2" />
                                                    <path
                                                        d="M 405.70 303.80 L 380.50 317.60 L 400.40 323.40 L 405.70 303.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="430" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="380.5" target-y="303.8" />
                                                    <path
                                                        d="M 434.00 288.40 L 408.70 302.20 L 428.70 308.00 L 434.00 288.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="432" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="408.7" target-y="288.4" />
                                                    <path
                                                        d="M 236.30 396.20 L 211.00 410.00 L 230.90 415.90 L 236.30 396.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="434" stroke="none" target-height="19.699982"
                                                        target-width="25.300003" target-x="211" target-y="396.2" />
                                                    <path
                                                        d="M 462.20 273.00 L 437.00 286.80 L 456.90 292.60 L 462.20 273.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="436" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="437" target-y="273" />
                                                    <path
                                                        d="M 287.80 390.00 L 262.60 403.80 L 282.50 409.70 L 287.80 390.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="438" stroke="none" target-height="19.700012"
                                                        target-width="25.199982" target-x="262.6" target-y="390" />
                                                    <path
                                                        d="M 316.10 374.60 L 290.80 388.40 L 310.80 394.30 L 316.10 374.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="440" stroke="none" target-height="19.699982"
                                                        target-width="25.300018" target-x="290.8" target-y="374.6" />
                                                    <path
                                                        d="M 344.30 359.20 L 319.10 373.00 L 339.00 378.90 L 344.30 359.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="442" stroke="none" target-height="19.699982"
                                                        target-width="25.199982" target-x="319.1" target-y="359.2" />
                                                    <path
                                                        d="M 372.60 343.80 L 347.30 357.60 L 367.30 363.50 L 372.60 343.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="444" stroke="none" target-height="19.700012"
                                                        target-width="25.300018" target-x="347.3" target-y="343.8" />
                                                    <path
                                                        d="M 400.80 328.40 L 375.60 342.20 L 395.50 348.00 L 400.80 328.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="446" stroke="none" target-height="19.600006"
                                                        target-width="25.199982" target-x="375.6" target-y="328.4" />
                                                    <path
                                                        d="M 429.10 313.00 L 403.80 326.80 L 423.80 332.60 L 429.10 313.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="448" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="403.8" target-y="313" />
                                                    <path
                                                        d="M 457.30 297.60 L 432.00 311.40 L 452.00 317.20 L 457.30 297.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="450" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="432" target-y="297.6" />
                                                    <path
                                                        d="M 259.60 405.40 L 234.30 419.20 L 254.30 425.10 L 259.60 405.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="452" stroke="none" target-height="19.700012"
                                                        target-width="25.300003" target-x="234.3" target-y="405.4" />
                                                    <path
                                                        d="M 231.30 420.80 L 206.10 434.60 L 226.00 440.50 L 231.30 420.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="454" stroke="none" target-height="19.700012"
                                                        target-width="25.199997" target-x="206.1" target-y="420.8" />
                                                    <path
                                                        d="M 513.80 266.80 L 488.50 280.60 L 508.50 286.40 L 513.80 266.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="456" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="488.5" target-y="266.8" />
                                                    <path
                                                        d="M 485.60 282.20 L 460.30 296.00 L 480.30 301.80 L 485.60 282.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="458" stroke="none" target-height="19.599976"
                                                        target-width="25.300018" target-x="460.3" target-y="282.2" />
                                                    <path
                                                        d="M 314.90 506.80 L 289.60 520.60 L 309.60 526.50 L 314.90 506.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="460" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="289.6" target-y="506.8" />
                                                    <path
                                                        d="M 343.10 491.40 L 317.80 505.20 L 337.80 511.00 L 343.10 491.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="462" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="317.8" target-y="491.4" />
                                                    <path
                                                        d="M 371.40 476.00 L 346.10 489.80 L 366.10 495.60 L 371.40 476.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="464" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="346.1" target-y="476" />
                                                    <path
                                                        d="M 399.60 460.60 L 374.30 474.40 L 394.30 480.20 L 399.60 460.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="466" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="374.3" target-y="460.6" />
                                                    <path
                                                        d="M 427.90 445.20 L 402.60 459.00 L 422.60 464.80 L 427.90 445.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="468" stroke="none" target-height="19.599976"
                                                        target-width="25.299988" target-x="402.6" target-y="445.2" />
                                                    <path
                                                        d="M 456.10 429.80 L 430.80 443.60 L 450.80 449.40 L 456.10 429.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="470" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="430.8" target-y="429.8" />
                                                    <path
                                                        d="M 484.40 414.40 L 459.10 428.10 L 479.10 434.00 L 484.40 414.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="472" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="459.1" target-y="414.4" />
                                                    <path
                                                        d="M 512.60 399.00 L 487.30 412.70 L 507.30 418.60 L 512.60 399.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="474" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="487.3" target-y="399" />
                                                    <path
                                                        d="M 544.30 359.80 L 519.00 373.50 L 539.00 379.40 L 544.30 359.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="476" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="519" target-y="359.8" />
                                                    <path
                                                        d="M 319.80 482.20 L 294.50 496.00 L 314.50 501.90 L 319.80 482.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="478" stroke="none" target-height="19.699982"
                                                        target-width="25.299988" target-x="294.5" target-y="482.2" />
                                                    <path
                                                        d="M 348.00 466.80 L 322.80 480.60 L 342.70 486.40 L 348.00 466.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="480" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="322.8" target-y="466.8" />
                                                    <path
                                                        d="M 376.30 451.40 L 351.00 465.20 L 371.00 471.00 L 376.30 451.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="482" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="351" target-y="451.4" />
                                                    <path
                                                        d="M 404.50 436.00 L 379.30 449.80 L 399.20 455.60 L 404.50 436.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="484" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="379.3" target-y="436" />
                                                    <path
                                                        d="M 432.80 420.60 L 407.50 434.40 L 427.50 440.20 L 432.80 420.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="486" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="407.5" target-y="420.6" />
                                                    <path
                                                        d="M 461.00 405.20 L 435.80 419.00 L 455.70 424.80 L 461.00 405.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="488" stroke="none" target-height="19.599976"
                                                        target-width="25.200012" target-x="435.8" target-y="405.2" />
                                                    <path
                                                        d="M 489.30 389.80 L 464.00 403.50 L 484.00 409.40 L 489.30 389.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="490" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="464" target-y="389.8" />
                                                    <path
                                                        d="M 291.50 497.60 L 266.30 511.40 L 286.20 517.30 L 291.50 497.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="492" stroke="none" target-height="19.699982"
                                                        target-width="25.200012" target-x="266.3" target-y="497.6" />
                                                    <path
                                                        d="M 263.30 513.00 L 238.00 526.80 L 258.00 532.70 L 263.30 513.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="494" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="238" target-y="513" />
                                                    <path
                                                        d="M 517.50 374.30 L 492.30 388.10 L 512.20 394.00 L 517.50 374.30 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="496" stroke="none" target-height="19.700012"
                                                        target-width="25.200012" target-x="492.3" target-y="374.3" />
                                                    <path
                                                        d="M 526.70 325.50 L 501.40 339.30 L 521.40 345.20 L 526.70 325.50 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="498" stroke="none" target-height="19.700012"
                                                        target-width="25.300018" target-x="501.4" target-y="325.5" />
                                                    <path
                                                        d="M 273.10 463.80 L 247.80 477.60 L 267.80 483.50 L 273.10 463.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="500" stroke="none" target-height="19.700012"
                                                        target-width="25.300003" target-x="247.8" target-y="463.8" />
                                                    <path
                                                        d="M 301.40 448.40 L 276.10 462.20 L 296.00 468.10 L 301.40 448.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="502" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="276.1" target-y="448.4" />
                                                    <path
                                                        d="M 329.60 433.00 L 304.30 446.80 L 324.30 452.70 L 329.60 433.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="504" stroke="none" target-height="19.700012"
                                                        target-width="25.300018" target-x="304.3" target-y="433" />
                                                    <path
                                                        d="M 357.80 417.60 L 332.60 431.40 L 352.50 437.20 L 357.80 417.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="506" stroke="none" target-height="19.600006"
                                                        target-width="25.199982" target-x="332.6" target-y="417.6" />
                                                    <path
                                                        d="M 386.10 402.20 L 360.80 416.00 L 380.80 421.80 L 386.10 402.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="508" stroke="none" target-height="19.599976"
                                                        target-width="25.300018" target-x="360.8" target-y="402.2" />
                                                    <path
                                                        d="M 414.40 386.80 L 389.10 400.60 L 409.00 406.40 L 414.40 386.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="510" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="389.1" target-y="386.8" />
                                                    <path
                                                        d="M 442.60 371.40 L 417.30 385.20 L 437.30 391.00 L 442.60 371.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="512" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="417.3" target-y="371.4" />
                                                    <path
                                                        d="M 244.90 479.20 L 219.60 493.00 L 239.60 498.90 L 244.90 479.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="514" stroke="none" target-height="19.699982"
                                                        target-width="25.299988" target-x="219.6" target-y="479.2" />
                                                    <path
                                                        d="M 499.10 340.60 L 473.80 354.30 L 493.80 360.20 L 499.10 340.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="516" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="473.8" target-y="340.6" />
                                                    <path
                                                        d="M 470.90 356.00 L 445.60 369.80 L 465.50 375.60 L 470.90 356.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="518" stroke="none" target-height="19.600006"
                                                        target-width="25.299988" target-x="445.6" target-y="356" />
                                                    <path
                                                        d="M 296.50 473.00 L 271.20 486.80 L 291.10 492.70 L 296.50 473.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="520" stroke="none" target-height="19.700012"
                                                        target-width="25.299988" target-x="271.2" target-y="473" />
                                                    <path
                                                        d="M 324.70 457.60 L 299.40 471.40 L 319.40 477.30 L 324.70 457.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="522" stroke="none" target-height="19.699982"
                                                        target-width="25.300018" target-x="299.4" target-y="457.6" />
                                                    <path
                                                        d="M 352.90 442.20 L 327.70 456.00 L 347.60 461.80 L 352.90 442.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="524" stroke="none" target-height="19.599976"
                                                        target-width="25.199982" target-x="327.7" target-y="442.2" />
                                                    <path
                                                        d="M 381.20 426.80 L 355.90 440.60 L 375.90 446.40 L 381.20 426.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="526" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="355.9" target-y="426.8" />
                                                    <path
                                                        d="M 409.40 411.40 L 384.20 425.20 L 404.10 431.00 L 409.40 411.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="528" stroke="none" target-height="19.600006"
                                                        target-width="25.199982" target-x="384.2" target-y="411.4" />
                                                    <path
                                                        d="M 437.70 396.00 L 412.40 409.80 L 432.40 415.60 L 437.70 396.00 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="530" stroke="none" target-height="19.600006"
                                                        target-width="25.300018" target-x="412.4" target-y="396" />
                                                    <path
                                                        d="M 465.90 380.60 L 440.70 394.40 L 460.60 400.20 L 465.90 380.60 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="532" stroke="none" target-height="19.600006"
                                                        target-width="25.199982" target-x="440.7" target-y="380.6" />
                                                    <path
                                                        d="M 268.20 488.40 L 242.90 502.20 L 262.90 508.10 L 268.20 488.40 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="534" stroke="none" target-height="19.700012"
                                                        target-width="25.300018" target-x="242.9" target-y="488.4" />
                                                    <path
                                                        d="M 549.20 335.20 L 523.90 348.90 L 543.90 354.80 L 549.20 335.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="536" stroke="none" target-height="19.599976"
                                                        target-width="25.299988" target-x="523.9" target-y="335.2" />
                                                    <path
                                                        d="M 522.40 349.80 L 497.20 363.50 L 517.10 369.40 L 522.40 349.80 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="538" stroke="none" target-height="19.600006"
                                                        target-width="25.200012" target-x="497.2" target-y="349.8" />
                                                    <path
                                                        d="M 494.20 365.20 L 468.90 378.90 L 488.90 384.80 L 494.20 365.20 Z"
                                                        fill="#007e97" fill-rule="nonzero" group-id="1,2,3,7,12"
                                                        node-id="540" stroke="none" target-height="19.599976"
                                                        target-width="25.300018" target-x="468.9" target-y="365.2" />
                                                </g>
                                            </g>
                                        </g>
                                        <path
                                            d="M 80.70 495.40 L 74.00 493.80 L 70.50 493.50 L 67.10 493.60 L 63.80 494.00 L 60.80 495.00 L 71.50 499.90 L 82.30 504.30 L 93.30 508.20 L 104.40 511.70 L 115.70 514.80 L 127.10 517.30 L 138.60 519.40 L 150.20 521.00 L 174.00 523.10 L 187.20 523.80 L 200.30 524.10 L 213.90 524.00 L 228.10 523.50 L 242.20 522.50 L 256.50 520.90 L 270.90 518.60 L 281.70 516.60 L 292.40 514.10 L 303.10 511.20 L 313.70 507.80 L 324.10 504.10 L 334.30 499.80 L 344.40 495.00 L 354.20 489.80 L 361.90 485.20 L 369.30 480.20 L 376.60 474.90 L 383.70 469.10 L 390.60 463.00 L 397.10 456.60 L 403.40 449.70 L 409.40 442.40 L 415.20 434.50 L 420.70 426.20 L 426.10 417.00 L 431.00 407.60 L 435.40 397.90 L 439.30 387.80 L 442.80 377.60 L 445.70 367.20 L 448.10 356.80 L 450.10 346.10 L 451.50 335.40 L 452.30 324.70 L 452.70 314.00 L 452.50 303.40 L 451.70 292.70 L 450.40 282.20 L 448.50 271.90 L 446.10 261.80 L 443.70 253.80 L 440.90 246.00 L 437.80 238.40 L 434.30 230.90 L 430.40 223.70 L 426.20 216.70 L 421.50 210.00 L 416.40 203.50 L 411.00 197.30 L 405.10 191.40 L 398.90 185.80 L 392.30 180.60 L 385.20 175.80 L 377.60 171.20 L 369.60 167.00 L 361.50 163.40 L 352.80 160.10 L 343.60 157.30 L 333.80 154.90 L 323.50 152.90 L 311.10 151.60 L 304.90 151.60 L 298.70 152.20 L 291.60 153.60 L 283.00 155.60 L 270.90 159.30 L 257.80 164.40 L 251.30 167.70 L 244.70 171.40 L 238.30 175.50 L 232.30 180.10 L 226.40 185.40 L 222.30 189.60 L 218.50 194.20 L 215.00 199.20 L 211.70 204.60 L 208.80 210.30 L 206.00 217.50 L 203.30 225.20 L 195.30 253.40 L 201.60 255.10 L 207.90 257.30 L 214.10 259.90 L 223.40 264.40 L 251.30 279.60 L 263.90 285.70 L 270.30 288.30 L 276.80 290.50 L 283.40 292.10 L 290.20 293.30 L 296.20 293.80 L 302.20 293.80 L 308.20 293.40 L 317.20 292.20 L 326.20 290.40 L 362.10 281.80 L 370.00 280.40 L 386.10 278.80 L 402.30 278.70 L 414.20 279.60 L 421.70 281.20 L 425.20 282.60 L 429.30 285.20 L 431.20 286.90 L 433.20 289.70 L 434.80 292.80 L 436.60 299.60 L 437.30 306.70 L 437.50 316.90 L 437.10 327.10 L 436.10 337.30 L 434.60 347.40 L 432.60 357.40 L 430.10 367.40 L 427.20 377.20 L 423.90 387.00 L 416.60 405.10 L 412.60 413.60 L 406.40 424.40 L 403.00 429.50 L 397.90 436.20 L 392.40 442.40 L 386.60 448.20 L 380.30 453.60 L 373.70 458.60 L 366.80 463.30 L 359.60 467.60 L 352.20 471.50 L 344.50 475.00 L 328.90 481.00 L 320.90 483.40 L 310.10 486.20 L 299.20 488.50 L 288.30 490.30 L 266.20 492.80 L 255.00 493.60 L 221.60 494.90 L 230.30 506.50 L 222.50 508.10 L 214.70 509.20 L 206.80 510.00 L 198.90 510.30 L 191.00 510.20 L 183.10 509.70 L 175.30 508.70 L 167.50 507.40 L 159.80 505.60 L 152.10 503.40 L 153.60 507.20 L 154.60 511.10 L 155.10 515.20 L 148.70 515.10 L 142.30 514.60 L 136.00 513.60 L 126.70 511.40 L 117.40 508.50 L 80.70 495.40 Z"
                                            fill="#009e8f" fill-rule="nonzero" group-id="1,2,3" node-id="544"
                                            stroke="none" target-height="372.49997" target-width="391.90002"
                                            target-x="60.8" target-y="151.6" />
                                        <path
                                            d="M 142.30 162.20 L 149.40 163.10 L 149.70 190.50 L 153.90 189.40 L 155.50 189.30 L 157.00 189.60 L 158.40 190.50 L 161.00 197.20 L 165.90 207.40 L 173.50 220.50 L 178.20 227.50 L 183.70 234.70 L 186.20 237.30 L 189.60 240.00 L 199.10 245.60 L 211.10 251.10 L 221.10 254.90 L 243.40 262.40 L 277.30 272.10 L 297.20 277.20 L 312.10 280.90 L 315.60 281.50 L 320.00 281.60 L 330.90 280.60 L 343.90 278.10 L 354.20 275.50 L 376.80 268.50 L 399.90 260.10 L 421.60 251.30 L 440.10 242.90 L 453.70 236.00 L 457.70 233.50 L 461.20 230.90 L 467.60 224.80 L 471.80 219.90 L 475.50 214.90 L 487.30 197.30 L 488.70 196.60 L 495.20 199.60 L 496.20 199.70 L 497.00 199.30 L 497.80 198.60 L 498.20 195.10 L 497.80 188.80 L 494.40 166.10 L 502.50 169.10 L 497.00 147.40 L 493.60 135.30 L 489.80 123.60 L 485.80 112.70 L 466.30 64.00 L 464.80 59.40 L 465.10 58.40 L 466.00 57.40 L 472.80 52.50 L 476.80 48.10 L 478.50 45.20 L 481.20 38.50 L 483.70 27.70 L 480.30 29.80 L 477.80 30.90 L 476.80 30.90 L 476.20 30.50 L 476.00 29.70 L 478.60 21.60 L 481.50 7.50 L 464.70 19.80 L 455.30 26.10 L 446.20 31.40 L 442.10 33.40 L 419.30 41.00 L 413.50 42.50 L 401.90 44.30 L 384.20 44.70 L 370.10 46.10 L 348.90 50.30 L 325.70 56.50 L 317.30 52.60 L 310.30 50.00 L 299.00 46.90 L 284.40 44.20 L 276.10 43.40 L 261.30 42.60 L 255.80 41.70 L 250.20 40.20 L 244.70 38.30 L 233.60 33.40 L 225.50 29.10 L 210.40 19.50 L 187.50 3.10 L 184.30 1.10 L 182.30 0.10 L 181.30 0.00 L 180.90 0.40 L 180.70 2.20 L 181.60 8.20 L 185.20 18.90 L 188.40 26.70 L 174.60 18.20 L 174.00 19.60 L 173.70 21.30 L 173.80 23.40 L 174.70 26.50 L 178.20 33.60 L 185.20 43.50 L 190.60 50.00 L 183.10 62.00 L 171.10 82.80 L 162.20 100.10 L 157.90 109.20 L 151.80 124.00 L 146.70 139.80 L 143.50 153.90 L 142.30 162.20 Z"
                                            fill="#009e8f" fill-rule="nonzero" group-id="1,2,3" node-id="546"
                                            stroke="none" target-height="281.6" target-width="360.2" target-x="142.3"
                                            target-y="0" />
                                        <g node-id="592">
                                            <path
                                                d="M 323.70 205.10 L 328.40 209.80 L 333.30 214.00 L 338.40 217.80 L 343.80 221.20 L 349.50 224.20 L 355.20 226.70 L 361.20 228.80 L 367.30 230.50 L 373.50 231.70 L 379.70 232.40 L 386.00 232.70 L 392.40 232.50 L 398.70 231.90 L 405.00 230.70 L 411.30 229.10 L 417.50 226.90 L 423.60 224.30 L 429.30 221.20 L 434.80 217.80 L 439.90 214.10 L 444.70 209.90 L 449.20 205.40 L 453.30 200.60 L 457.00 195.60 L 460.40 190.20 L 463.40 184.70 L 465.90 178.90 L 468.00 172.90 L 469.70 166.70 L 470.90 160.40 L 471.70 154.00 L 471.90 147.40 L 471.70 140.80 L 470.90 134.40 L 469.70 128.10 L 468.00 121.90 L 465.90 115.90 L 463.40 110.10 L 460.40 104.60 L 457.00 99.20 L 453.30 94.20 L 449.20 89.40 L 444.70 84.90 L 439.90 80.70 L 434.80 77.00 L 429.30 73.60 L 423.60 70.50 L 417.50 67.90 L 411.30 65.70 L 405.00 64.10 L 398.70 62.90 L 392.40 62.30 L 386.00 62.10 L 379.70 62.40 L 373.50 63.10 L 367.30 64.30 L 361.20 66.00 L 355.20 68.10 L 349.50 70.60 L 343.80 73.60 L 338.40 77.00 L 333.30 80.80 L 328.40 85.00 L 323.70 89.70 L 319.10 85.00 L 314.20 80.80 L 309.00 77.00 L 303.60 73.60 L 298.00 70.60 L 292.20 68.10 L 286.30 66.00 L 280.20 64.30 L 274.00 63.10 L 267.70 62.40 L 261.40 62.10 L 255.10 62.30 L 248.70 62.90 L 242.40 64.10 L 236.20 65.70 L 229.90 67.90 L 223.80 70.50 L 218.10 73.60 L 212.70 77.00 L 207.60 80.70 L 202.70 84.90 L 198.30 89.40 L 194.20 94.20 L 190.40 99.20 L 187.10 104.60 L 184.10 110.10 L 181.60 115.90 L 179.40 121.90 L 177.70 128.10 L 176.50 134.40 L 175.80 140.80 L 175.50 147.40 L 175.80 154.00 L 176.50 160.40 L 177.70 166.70 L 179.40 172.90 L 181.60 178.90 L 184.10 184.70 L 187.10 190.20 L 190.40 195.60 L 194.20 200.60 L 198.30 205.40 L 202.70 209.90 L 207.60 214.10 L 212.70 217.80 L 218.10 221.20 L 223.80 224.30 L 229.90 226.90 L 236.20 229.10 L 242.40 230.70 L 248.70 231.90 L 255.10 232.50 L 261.40 232.70 L 267.70 232.40 L 274.00 231.70 L 280.20 230.50 L 286.30 228.80 L 292.20 226.70 L 298.00 224.20 L 303.60 221.20 L 309.00 217.80 L 314.20 214.00 L 319.10 209.80 L 323.70 205.10 Z"
                                                fill="#a5d7d3" fill-opacity="0.8" fill-rule="nonzero" group-id="1,2,3,8"
                                                node-id="550" stroke="none" target-height="170.6" target-width="296.4"
                                                target-x="175.5" target-y="62.1" />
                                        </g>
                                        <path
                                            d="M 324.70 241.20 L 327.90 234.80 L 333.50 225.20 L 337.40 219.70 L 341.70 214.30 L 346.60 209.10 L 350.20 205.90 L 354.00 203.20 L 360.10 199.40 L 361.50 198.10 L 362.70 195.80 L 362.60 194.10 L 361.90 192.30 L 360.40 190.30 L 356.40 186.80 L 349.20 182.40 L 339.40 177.70 L 324.70 172.00 L 310.00 177.70 L 300.30 182.40 L 293.10 186.80 L 289.00 190.40 L 287.60 192.30 L 286.90 194.10 L 286.80 195.80 L 288.00 198.10 L 289.30 199.40 L 295.50 203.20 L 299.30 205.90 L 302.90 209.10 L 307.80 214.30 L 312.10 219.70 L 315.90 225.20 L 321.60 234.80 L 324.70 241.20 Z"
                                            fill="#f59a00" fill-rule="nonzero" group-id="1,2,3" node-id="553"
                                            stroke="none" target-height="69.2" target-width="75.900024" target-x="286.8"
                                            target-y="172" />
                                        <path
                                            d="M 323.70 169.40 L 326.00 175.00 L 328.70 180.30 L 331.80 185.30 L 335.30 189.90 L 339.20 194.20 L 343.40 198.10 L 347.90 201.60 L 352.80 204.70 L 357.80 207.40 L 363.10 209.70 L 368.50 211.50 L 374.20 212.80 L 379.90 213.70 L 385.70 214.00 L 391.70 213.80 L 397.70 213.00 L 403.70 211.70 L 409.40 210.00 L 414.80 207.70 L 419.90 205.00 L 424.80 201.90 L 429.40 198.40 L 433.60 194.60 L 437.50 190.30 L 441.00 185.80 L 444.10 181.00 L 446.70 175.90 L 449.00 170.60 L 450.80 165.10 L 452.10 159.40 L 452.90 153.50 L 453.20 147.40 L 452.90 141.30 L 452.10 135.40 L 450.80 129.70 L 449.00 124.20 L 446.70 118.80 L 444.10 113.80 L 441.00 109.00 L 437.50 104.40 L 433.60 100.20 L 429.40 96.30 L 424.80 92.90 L 419.90 89.70 L 414.80 87.00 L 409.40 84.80 L 403.70 83.00 L 397.70 81.70 L 391.70 81.00 L 385.70 80.80 L 379.90 81.10 L 374.20 81.90 L 368.50 83.30 L 363.10 85.10 L 357.80 87.30 L 352.80 90.00 L 347.90 93.10 L 343.40 96.70 L 339.20 100.60 L 335.30 104.90 L 331.80 109.50 L 328.70 114.40 L 326.00 119.70 L 323.70 125.40 L 321.40 119.70 L 318.70 114.40 L 315.70 109.50 L 312.10 104.90 L 308.20 100.60 L 304.00 96.70 L 299.50 93.10 L 294.70 90.00 L 289.60 87.30 L 284.40 85.10 L 278.90 83.30 L 273.30 81.90 L 267.50 81.10 L 261.70 80.80 L 255.80 81.00 L 249.70 81.70 L 243.80 83.00 L 238.10 84.80 L 232.70 87.00 L 227.60 89.70 L 222.70 92.90 L 218.10 96.30 L 213.90 100.20 L 210.00 104.40 L 206.50 109.00 L 203.40 113.80 L 200.70 118.80 L 198.40 124.20 L 196.70 129.70 L 195.40 135.40 L 194.60 141.30 L 194.30 147.40 L 194.60 153.50 L 195.40 159.40 L 196.70 165.10 L 198.40 170.60 L 200.70 175.90 L 203.40 181.00 L 206.50 185.80 L 210.00 190.30 L 213.90 194.60 L 218.10 198.40 L 222.70 201.90 L 227.60 205.00 L 232.70 207.70 L 238.10 210.00 L 243.80 211.70 L 249.70 213.00 L 255.80 213.80 L 261.70 214.00 L 267.50 213.70 L 273.30 212.80 L 278.90 211.50 L 284.40 209.70 L 289.60 207.40 L 294.70 204.70 L 299.50 201.60 L 304.00 198.10 L 308.20 194.20 L 312.10 189.90 L 315.70 185.30 L 318.70 180.30 L 321.40 175.00 L 323.70 169.40 Z"
                                            fill="#fde3d8" fill-rule="nonzero" group-id="1,2,3" node-id="555"
                                            stroke="none" target-height="133.2" target-width="258.90002"
                                            target-x="194.3" target-y="80.8" />
                                        <path
                                            d="M 308.10 164.50 L 307.90 169.00 L 307.20 173.20 L 306.20 177.30 L 304.70 181.30 L 300.70 188.60 L 295.50 195.00 L 289.10 200.20 L 281.80 204.20 L 277.80 205.70 L 273.70 206.70 L 269.40 207.40 L 265.00 207.60 L 260.50 207.40 L 256.20 206.70 L 252.10 205.70 L 248.20 204.20 L 240.80 200.20 L 234.50 195.00 L 229.20 188.60 L 225.20 181.30 L 223.80 177.30 L 222.70 173.20 L 222.10 169.00 L 221.80 164.50 L 222.10 160.00 L 222.70 155.70 L 223.80 151.60 L 225.20 147.70 L 229.20 140.30 L 234.50 134.00 L 240.80 128.70 L 248.20 124.70 L 252.10 123.30 L 256.20 122.20 L 260.50 121.60 L 265.00 121.30 L 269.40 121.60 L 273.70 122.20 L 277.80 123.30 L 281.80 124.70 L 289.10 128.70 L 295.50 134.00 L 300.70 140.30 L 304.70 147.70 L 306.20 151.60 L 307.20 155.70 L 307.90 160.00 L 308.10 164.50 Z"
                                            fill="#005560" fill-rule="nonzero" group-id="1,2,3" node-id="557"
                                            stroke="none" target-height="86.3" target-width="86.3" target-x="221.8"
                                            target-y="121.3" />
                                        <path
                                            d="M 300.90 153.70 L 300.70 150.90 L 300.00 148.30 L 298.80 146.00 L 297.30 143.80 L 295.50 142.00 L 293.30 140.50 L 291.00 139.40 L 288.40 138.70 L 285.60 138.40 L 282.80 138.70 L 280.30 139.40 L 277.90 140.50 L 275.80 142.10 L 274.00 143.90 L 272.40 146.00 L 271.30 148.40 L 270.60 150.90 L 270.40 153.70 L 270.60 156.50 L 271.30 159.10 L 272.40 161.40 L 274.00 163.60 L 275.80 165.40 L 277.90 166.90 L 280.30 168.00 L 282.90 168.70 L 285.60 169.00 L 288.60 168.70 L 291.50 167.80 L 294.10 166.40 L 296.50 164.50 L 298.40 162.20 L 299.80 159.50 L 300.60 156.70 L 300.90 153.70 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="559"
                                            stroke="none" target-height="30.600006" target-width="30.5" target-x="270.4"
                                            target-y="138.4" />
                                        <path
                                            d="M 257.30 181.60 L 257.00 179.30 L 256.20 177.20 L 254.80 175.50 L 253.10 174.20 L 251.10 173.30 L 248.80 173.00 L 246.50 173.30 L 244.50 174.20 L 242.80 175.50 L 241.40 177.20 L 240.60 179.30 L 240.30 181.60 L 240.60 183.80 L 241.40 185.90 L 242.80 187.60 L 244.50 188.90 L 246.50 189.80 L 248.80 190.10 L 251.10 189.80 L 253.10 188.90 L 254.80 187.60 L 256.20 185.90 L 257.00 183.80 L 257.30 181.60 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="561"
                                            stroke="none" target-height="17.100006" target-width="16.999985"
                                            target-x="240.3" target-y="173" />
                                        <path
                                            d="M 429.00 161.20 L 428.80 165.70 L 428.10 170.00 L 427.10 174.10 L 425.60 178.00 L 421.60 185.40 L 416.40 191.70 L 410.00 197.00 L 402.60 201.00 L 398.70 202.40 L 394.60 203.50 L 390.30 204.10 L 385.90 204.40 L 381.40 204.10 L 377.10 203.50 L 373.00 202.40 L 369.10 201.00 L 365.30 199.10 L 358.40 194.50 L 352.60 188.70 L 347.90 181.80 L 346.10 178.00 L 344.60 174.10 L 343.60 170.00 L 342.90 165.70 L 342.70 161.20 L 342.90 156.80 L 343.60 152.50 L 344.60 148.40 L 346.10 144.40 L 347.90 140.60 L 352.60 133.80 L 358.40 127.90 L 361.70 125.50 L 369.10 121.50 L 373.00 120.00 L 377.10 119.00 L 381.40 118.30 L 385.90 118.10 L 390.30 118.30 L 394.60 119.00 L 398.70 120.00 L 402.60 121.50 L 410.00 125.50 L 416.40 130.70 L 421.60 137.10 L 425.60 144.40 L 427.10 148.40 L 428.10 152.50 L 428.80 156.80 L 429.00 161.20 Z"
                                            fill="#005560" fill-rule="nonzero" group-id="1,2,3" node-id="563"
                                            stroke="none" target-height="86.299995" target-width="86.29999"
                                            target-x="342.7" target-y="118.1" />
                                        <path
                                            d="M 421.80 150.50 L 421.50 147.40 L 420.60 144.60 L 419.20 142.00 L 417.40 139.70 L 415.10 137.80 L 412.40 136.30 L 409.40 135.40 L 406.50 135.20 L 403.50 135.50 L 400.70 136.30 L 398.10 137.70 L 395.70 139.60 L 393.80 142.00 L 392.40 144.60 L 391.50 147.50 L 391.20 150.40 L 391.50 153.30 L 392.40 156.30 L 393.90 159.00 L 395.70 161.30 L 398.00 163.10 L 400.60 164.50 L 403.50 165.40 L 406.50 165.70 L 409.30 165.50 L 411.90 164.80 L 414.20 163.60 L 416.40 162.10 L 418.20 160.30 L 419.70 158.20 L 420.80 155.80 L 421.50 153.20 L 421.80 150.50 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="565"
                                            stroke="none" target-height="30.5" target-width="30.599976" target-x="391.2"
                                            target-y="135.2" />
                                        <path
                                            d="M 378.20 178.30 L 377.60 175.00 L 375.70 172.30 L 372.90 170.40 L 371.30 169.90 L 368.00 169.90 L 365.00 171.20 L 362.50 173.60 L 361.30 176.60 L 361.30 179.90 L 362.60 183.10 L 363.60 184.40 L 366.40 186.20 L 369.70 186.80 L 372.00 186.50 L 374.00 185.70 L 375.70 184.30 L 377.00 182.60 L 377.90 180.60 L 378.20 178.30 Z"
                                            fill="#ffffff" fill-rule="nonzero" group-id="1,2,3" node-id="567"
                                            stroke="none" target-height="16.90001" target-width="16.900024"
                                            target-x="361.3" target-y="169.9" />
                                        <path
                                            d="M 102.20 473.70 L 101.30 475.20 L 101.20 476.30 L 101.60 477.20 L 102.60 478.10 L 104.50 478.80 L 107.50 479.40 L 112.00 479.80 L 122.00 479.40 L 138.60 477.10 L 150.30 474.80 L 162.30 471.80 L 175.40 467.90 L 193.10 461.70 L 202.20 458.10 L 207.80 455.40 L 213.50 452.20 L 219.10 448.60 L 224.70 444.30 L 230.00 439.80 L 240.10 429.40 L 245.00 423.60 L 253.90 411.20 L 261.80 397.60 L 265.20 390.40 L 268.30 383.10 L 271.10 375.60 L 273.50 367.90 L 275.40 360.10 L 276.90 352.30 L 278.00 344.30 L 278.60 336.30 L 278.60 328.30 L 278.20 320.30 L 277.20 312.30 L 275.60 304.30 L 274.00 298.10 L 271.90 291.90 L 269.40 285.80 L 266.50 279.70 L 263.20 273.70 L 259.00 267.30 L 254.80 261.90 L 250.70 257.20 L 246.50 253.20 L 241.80 249.50 L 237.30 246.40 L 232.70 243.90 L 228.10 241.90 L 218.60 239.10 L 209.30 237.90 L 203.10 237.80 L 197.10 238.20 L 185.50 240.30 L 175.10 243.40 L 168.00 246.30 L 156.90 251.80 L 150.30 255.90 L 138.40 266.90 L 132.40 273.30 L 123.80 283.10 L 89.00 325.90 L 80.20 336.10 L 74.20 342.60 L 62.20 354.30 L 55.90 359.50 L 44.70 367.70 L 36.50 372.80 L 29.40 376.70 L 11.30 385.70 L 7.00 388.20 L 2.30 392.00 L 0.60 394.10 L 0.10 395.20 L 0.30 396.90 L 1.10 397.80 L 4.40 399.60 L 11.60 401.40 L 17.20 402.20 L 29.30 403.30 L 41.70 403.90 L 63.70 404.00 L 44.40 411.10 L 33.60 415.60 L 23.20 420.70 L 18.50 423.40 L 12.50 427.80 L 9.80 431.10 L 9.50 432.10 L 9.60 432.90 L 10.10 433.70 L 11.20 434.40 L 15.40 435.60 L 18.80 436.10 L 24.00 436.40 L 37.20 436.40 L 58.70 435.00 L 83.70 432.50 L 73.60 438.20 L 63.90 444.20 L 53.80 451.40 L 49.20 455.40 L 45.40 459.10 L 41.60 464.30 L 40.60 466.60 L 40.50 467.80 L 40.80 468.60 L 41.70 469.30 L 45.50 469.90 L 52.90 469.20 L 59.50 467.90 L 75.90 463.60 L 102.60 455.10 L 118.50 449.40 L 130.40 444.90 L 136.50 442.20 L 116.10 459.90 L 108.20 467.20 L 102.20 473.70 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="569"
                                            stroke="none" target-height="241.99998" target-width="278.5" target-x="0.1"
                                            target-y="237.8" />
                                        <g node-id="593">
                                            <path
                                                d="M 177.90 412.00 L 180.70 412.70 L 184.00 411.90 L 188.70 408.80 L 195.30 402.20 L 199.30 397.30 L 207.20 386.40 L 219.80 366.90 L 223.20 361.80 L 224.50 360.50 L 225.30 360.20 L 225.70 360.40 L 226.20 361.50 L 227.40 376.30 L 228.10 381.40 L 228.70 383.30 L 229.40 384.40 L 230.20 384.90 L 231.10 384.80 L 232.40 384.20 L 234.10 382.80 L 236.20 380.00 L 240.00 373.20 L 242.10 368.30 L 245.50 357.70 L 248.40 344.60 L 250.10 329.90 L 250.30 322.30 L 249.90 315.60 L 249.00 309.50 L 247.70 303.50 L 246.10 298.30 L 244.30 293.70 L 242.10 289.30 L 238.10 282.50 L 235.00 278.40 L 230.50 273.60 L 226.90 271.30 L 223.20 269.60 L 219.60 268.30 L 216.00 267.40 L 211.00 266.90 L 206.00 267.00 L 201.00 267.80 L 196.00 269.10 L 191.00 270.80 L 185.90 273.10 L 176.10 278.60 L 164.20 287.00 L 157.40 292.40 L 133.80 312.80 L 96.10 344.00 L 86.90 352.10 L 78.40 360.00 L 68.10 370.50 L 64.00 375.30 L 61.90 378.90 L 61.70 381.30 L 62.30 382.60 L 63.50 383.80 L 67.70 385.60 L 73.70 386.60 L 78.70 386.90 L 90.10 386.70 L 106.20 385.30 L 116.70 383.80 L 108.10 415.70 L 107.90 417.40 L 108.10 418.50 L 108.60 419.10 L 109.40 419.50 L 110.90 419.50 L 116.00 418.10 L 120.50 416.00 L 131.90 409.40 L 150.30 397.10 L 171.80 381.40 L 170.40 390.10 L 170.30 393.90 L 170.90 400.80 L 172.60 406.00 L 173.90 408.30 L 175.70 410.30 L 177.90 412.00 Z"
                                                fill="#a5d7d3" fill-opacity="0.8" fill-rule="nonzero" group-id="1,2,3,9"
                                                node-id="573" stroke="none" target-height="152.6" target-width="188.6"
                                                target-x="61.7" target-y="266.9" />
                                        </g>
                                        <g node-id="594">
                                            <path
                                                d="M 430.50 69.20 L 437.20 72.10 L 443.50 75.50 L 449.50 79.30 L 455.00 83.60 L 460.20 88.30 L 465.00 93.40 L 469.30 98.80 L 473.20 104.60 L 476.60 110.70 L 479.50 117.00 L 481.90 123.50 L 483.80 130.30 L 485.10 137.20 L 485.90 144.20 L 486.10 151.40 L 485.60 158.70 L 484.60 166.00 L 483.00 173.00 L 480.80 179.70 L 478.20 186.20 L 474.90 192.50 L 471.30 198.40 L 467.20 204.00 L 462.60 209.30 L 457.70 214.20 L 452.40 218.60 L 446.70 222.60 L 440.60 226.20 L 434.30 229.30 L 427.70 231.90 L 420.80 234.00 L 413.70 235.50 L 406.70 236.30 L 399.70 236.60 L 392.80 236.40 L 385.90 235.50 L 379.10 234.20 L 372.30 232.20 L 362.20 228.20 L 356.30 225.00 L 350.60 221.50 L 345.40 217.60 L 340.40 213.30 L 335.80 208.70 L 331.60 203.80 L 327.80 198.60 L 324.30 193.10 L 321.40 187.40 L 318.80 181.50 L 316.70 175.40 L 315.00 169.10 L 313.80 162.80 L 313.10 156.30 L 312.90 149.70 L 313.10 143.20 L 313.90 136.60 L 315.20 130.00 L 317.10 123.30 L 319.50 116.80 L 322.30 110.60 L 325.60 104.80 L 329.20 99.40 L 333.30 94.20 L 337.60 89.30 L 342.30 84.90 L 347.30 80.80 L 352.70 77.10 L 358.20 73.90 L 363.90 71.00 L 369.90 68.60 L 376.10 66.60 L 382.40 65.10 L 388.80 64.00 L 395.40 63.50 L 401.90 63.40 L 408.50 63.90 L 415.20 64.80 L 422.00 66.40 L 430.50 69.20 Z"
                                                fill="#ffffff" fill-opacity="0.33" fill-rule="nonzero"
                                                group-id="1,2,3,10" node-id="578" stroke="none"
                                                target-height="173.20001" target-width="173.20001" target-x="312.9"
                                                target-y="63.4" />
                                        </g>
                                        <path
                                            d="M 308.20 115.10 L 305.80 122.10 L 304.00 129.00 L 302.70 136.00 L 301.90 143.10 L 301.70 150.20 L 302.00 157.20 L 302.70 164.20 L 304.00 171.10 L 305.70 177.90 L 308.00 184.50 L 310.70 191.00 L 313.90 197.30 L 317.50 203.40 L 321.60 209.20 L 326.20 214.80 L 331.30 220.10 L 336.70 225.00 L 342.40 229.40 L 348.30 233.40 L 354.50 236.90 L 360.90 239.90 L 367.50 242.40 L 374.10 244.50 L 381.00 246.10 L 388.00 247.10 L 394.90 247.70 L 402.00 247.80 L 409.00 247.40 L 416.10 246.40 L 423.00 244.90 L 429.90 242.90 L 436.80 240.40 L 439.00 243.80 L 541.90 369.60 L 544.00 371.80 L 546.40 373.50 L 549.00 374.70 L 551.80 375.50 L 554.60 375.80 L 557.40 375.60 L 560.20 374.90 L 562.90 373.70 L 568.00 369.90 L 570.20 367.70 L 571.90 365.40 L 573.10 362.80 L 573.90 360.00 L 574.10 357.20 L 574.00 354.30 L 573.30 351.50 L 572.10 348.90 L 570.40 346.30 L 467.50 220.40 L 472.70 215.00 L 477.40 209.30 L 481.60 203.30 L 485.30 197.10 L 488.50 190.60 L 491.30 183.90 L 493.50 177.10 L 495.20 170.10 L 496.40 163.00 L 497.10 155.90 L 497.30 148.70 L 496.90 141.40 L 496.00 134.20 L 494.50 127.10 L 492.50 120.00 L 489.90 112.90 L 486.80 106.10 L 483.20 99.60 L 479.20 93.50 L 474.80 87.80 L 470.00 82.30 L 464.80 77.30 L 459.30 72.70 L 453.40 68.60 L 447.30 64.80 L 440.90 61.50 L 434.30 58.70 L 427.40 56.40 L 420.30 54.60 L 413.20 53.30 L 405.80 52.50 L 398.30 52.30 L 390.80 52.70 L 383.50 53.70 L 376.30 55.10 L 369.30 57.10 L 362.50 59.60 L 355.90 62.60 L 349.60 66.00 L 343.60 69.90 L 337.80 74.20 L 332.40 78.90 L 327.40 84.00 L 322.70 89.60 L 318.40 95.40 L 314.50 101.60 L 311.10 108.20 L 308.20 115.10 Z M 469.90 176.90 L 467.40 182.70 L 464.60 188.10 L 461.40 193.10 L 457.80 197.80 L 453.90 202.20 L 449.70 206.30 L 445.20 210.00 L 440.40 213.30 L 435.40 216.30 L 430.30 218.80 L 424.90 221.00 L 419.40 222.70 L 413.70 224.00 L 408.00 224.90 L 402.20 225.40 L 396.20 225.30 L 390.40 224.80 L 384.50 223.90 L 378.60 222.40 L 372.60 220.40 L 366.90 217.90 L 361.50 215.10 L 356.50 211.90 L 351.70 208.30 L 347.30 204.40 L 343.30 200.20 L 339.60 195.70 L 336.20 190.90 L 333.30 185.90 L 330.70 180.80 L 328.60 175.40 L 326.80 169.90 L 325.50 164.20 L 324.60 158.50 L 324.20 152.70 L 324.20 146.70 L 324.70 140.90 L 325.70 135.00 L 327.10 129.10 L 329.10 123.10 L 331.60 117.40 L 334.50 112.00 L 337.70 107.00 L 341.20 102.20 L 345.20 97.80 L 349.40 93.80 L 353.80 90.10 L 358.60 86.70 L 363.60 83.80 L 368.80 81.20 L 374.10 79.10 L 379.70 77.30 L 385.30 76.00 L 391.00 75.10 L 396.90 74.70 L 402.80 74.70 L 408.70 75.20 L 414.60 76.20 L 420.50 77.60 L 426.40 79.60 L 432.20 82.10 L 437.60 85.00 L 442.60 88.20 L 447.30 91.70 L 451.70 95.70 L 455.80 99.90 L 459.50 104.40 L 462.80 109.10 L 465.70 114.10 L 468.30 119.30 L 470.40 124.60 L 472.20 130.20 L 473.50 135.80 L 474.40 141.60 L 474.80 147.40 L 474.80 153.30 L 474.30 159.20 L 473.40 165.10 L 471.90 171.00 L 469.90 176.90 Z"
                                            fill="#f8d590" fill-rule="nonzero" group-id="1,2,3" node-id="581"
                                            stroke="none" target-height="323.5" target-width="272.39996"
                                            target-x="301.7" target-y="52.3" />
                                        <path
                                            d="M 492.30 244.00 L 437.90 287.00 L 444.10 294.20 L 451.00 300.40 L 455.20 303.40 L 459.90 306.00 L 463.30 307.30 L 466.70 308.30 L 470.40 309.00 L 474.00 309.10 L 477.80 308.70 L 481.80 307.60 L 487.60 304.90 L 490.60 302.80 L 504.70 290.90 L 515.70 283.30 L 520.80 280.50 L 525.60 278.40 L 532.20 276.70 L 535.60 276.60 L 457.30 331.00 L 467.90 335.80 L 480.10 339.70 L 487.30 341.30 L 495.80 342.60 L 507.70 343.30 L 514.30 343.10 L 520.80 342.50 L 527.60 341.40 L 534.80 339.70 L 541.80 337.50 L 549.00 334.60 L 556.50 331.00 L 540.90 343.50 L 516.80 362.00 L 494.70 377.80 L 485.20 383.80 L 627.10 397.60 L 629.40 383.10 L 630.10 375.90 L 630.70 367.20 L 630.80 358.00 L 629.80 340.80 L 627.60 326.30 L 625.90 319.00 L 623.70 311.70 L 621.00 304.40 L 617.80 297.30 L 614.00 290.40 L 609.50 283.70 L 605.80 278.90 L 601.70 274.30 L 597.10 269.90 L 592.10 265.70 L 586.80 261.90 L 581.00 258.30 L 574.50 255.00 L 567.50 252.00 L 560.40 249.50 L 552.60 247.40 L 544.00 245.60 L 534.60 244.30 L 525.40 243.40 L 515.40 243.10 L 504.30 243.30 L 492.30 244.00 Z"
                                            fill="#007e97" fill-rule="nonzero" group-id="1,2,3" node-id="583"
                                            stroke="none" target-height="154.5" target-width="192.9" target-x="437.9"
                                            target-y="243.1" />
                                    </g>
                                </g>
                            </g>
                        </svg>
                        <p class="text-gray-600 text-sm dark:text-gray-400">我这知识啊，都学杂了~</p>
                    </div>
                </div>
                <!-- Modal footer -->
                <div class="p-4 md:p-5 border-t text-xs flex items-center text-gray-400 dark:border-gray-600">
                    <!-- Esc 退出提示 -->
                    <span class="px-2 py-[1px] flex-none border rounded dark:border-gray-600">Esc</span>
                    <span class="text-gray-400 ml-2">关闭</span>

                    <!-- 底层技术介绍 -->
                    <span class="ml-auto">基于 <a href="https://lucene.apache.org/" target="_blank"
                            class="underline">Apache Lucene</a> 全文检索引擎开发</span>
                </div>
            </div>
        </div>
    </div>
<!-- 知识库目录 drawer component -->
<div id="wiki-catalog-drawer"
        class="block lg:hidden top-[4.3rem] fixed left-0 z-20 h-screen p-4 overflow-y-auto transition-transform -translate-x-full bg-white w-80 dark:bg-[#0d1117]"
        tabindex="-1" aria-labelledby="drawer-label">
        <!-- 知识库目录 -->
        <div class="grow transition-all duration-300">
            <div id="drawer-accordion-flush" data-accordion="collapse"
                data-active-classes="bg-white dark:bg-[#0d1117] dark:text-gray-300" data-inactive-classes=""
                class="last:pb-[170px]">
                <div v-for="(catalog, index) in props.catalogs" :key="index">
                    <h2 :id="'drawer-accordion-flush-heading-' + catalog.id">
                        <button type="button" class="hover:bg-gray-100 flex items-center justify-between w-full py-3 px-3 rounded-lg 
                            font-medium rtl:text-right text-gray-600 dark:text-gray-400 gap-3 dark:hover:bg-gray-800"
                            :data-accordion-target="'#drawer-accordion-flush-body-' + catalog.id"
                            :aria-expanded="catalog.children?.some(item => item?.articleId == route.query.articleId)"
                            :aria-controls="'drawer-accordion-flush-body-' + catalog.id">
                            <!-- 一级目录标题 -->
                            <span class="flex items-center" v-html="catalog.title"></span>
                            <!-- 箭头 -->
                            <svg data-accordion-icon class="w-3 h-3 rotate-90 transition-all shrink-0" aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M9 5 5 1 1 5" />
                            </svg>
                        </button>
                    </h2>
                    <!-- 二级目录 -->
                    <ul :id="'drawer-accordion-flush-body-' + catalog.id" class="hidden"
                        :aria-labelledby="'drawer-accordion-flush-heading-' + catalog.id">
                        <!-- 二级目录标题 -->
                        <li v-for="(childCatalog, index2) in catalog.children" :key="index2" class="flex items-center ps-10 py-2 pe-3 rounded-lg cursor-pointer 
                                    dark:text-gray-400"
                            :class="[childCatalog.articleId == route.query.articleId ? 'bg-sky-50 text-sky-600 dark:bg-sky-950 dark:text-sky-500' : 'hover:bg-gray-100 dark:hover:bg-gray-800']"
                            @click="goWikiArticleDetailPage(childCatalog.articleId)" v-html="childCatalog.title">
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>


</template>

<script setup>
import { onMounted, ref, onBeforeUnmount, watch } from 'vue'
import { useUserStore } from '@/stores/user'
import { useBlogSettingsStore } from '@/stores/blogsettings';
import { useRouter, useRoute } from 'vue-router';
import { showMessage } from '@/composables/util';
import { getArticleSearchPageList } from '@/api/frontend/search';
import { useDark, useToggle } from '@vueuse/core'
import { initCollapses, initDropdowns, initModals, initAccordions, Modal, Drawer } from 'flowbite'


// 初始化 flowbit 相关组件
const searchModal = ref(null)

//是否登陆，通过userStore中的userInfo对象是否有数据来判断
const userStore = useUserStore()
//获取userInfo对象所有属性名称的数组
const keys = Object.keys(userStore.userInfo)
//若大于零，则表示用户已登陆
const isLogined = ref(keys.length > 0)
// 引入博客设置信息 store
const blogSettingsStore = useBlogSettingsStore()
const router = useRouter()
const route = useRoute()

// 当前路由地址
const currPath = ref(route.path)

// 文章搜索结果
const searchArticles = ref([])
// 当前页码，默认值给1
const current = ref(1)
// 总数据量，默认值给0
const total = ref(0)
// 每页显示的数据量，默认值给10
const size = ref(10)
// 总共多少页
const pages = ref(0)
// 搜索关键词
const searchWord = ref('')

// 搜索 Loading
const searchLoading = ref(false)

const isDark = useDark()
const toggleDark = useToggle(isDark)

// 知识库目录 Drawer 引用
const wikiDrawer = ref(null)

// 知识库目录抽屉是否展开，默认未展开
const isDrawerExpand = ref(false)

// options with default values
const drawerOptions = {
    placement: 'right',
    backdrop: true,
    bodyScrolling: false,
    edge: false,
    edgeOffset: '',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-[19] block lg:hidden',
    onHide: () => {
        console.log('drawer is hidden');
        isDrawerExpand.value = false
    },
    onShow: () => {
        console.log('drawer is shown');
    },
    onToggle: () => {
        console.log('drawer has been toggled');
    },
};

// instance options object
const instanceOptionsDrawer = {
    id: 'wiki-catalog-drawer',
    override: true
};


// 侦听属性，监听searchWord的变化
watch(searchWord, (newText, oldText) => {
    console.log(`新值：${newText},旧值：${oldText}`)
    if (newText && newText != oldText) {//若不为空也不为旧值
        renderSearchArticles({ current: current.value, size: size.value, word: newText })
    } else if (newText == '') {
        searchArticles.value = []
    }
})

// 侦听 isDrawerExpand 变量
watch(isDrawerExpand, (newText, oldText) => {
    if (newText) {
        // 展示抽屉
        wikiDrawer.value.show()
    } else {
        // 隐藏抽屉
        wikiDrawer.value.hide()
    }
})

function renderSearchArticles(data) {
    // 显示加载 Loading
    searchLoading.value = true
    getArticleSearchPageList(data).then(res => {
        console.log(res)
        if (res.success) {
            searchArticles.value = res.data
            current.value = res.current
            size.value = res.size
            total.value = res.total
            pages.value = res.pages
        }
    }).finally(() => searchLoading.value = false)
}

// 渲染下一页搜索结果
const nextPage = () => {
    renderSearchArticles({ current: current.value + 1, size: size.value, word: searchWord.value })
}

// 渲染上一页搜索结果
const prePage = () => {
    renderSearchArticles({ current: current.value - 1, size: size.value, word: searchWord.value })
}


const logout = () => {
    userStore.logout()
    // 标记为未登录
    isLogined.value = false
    showMessage('退出登录成功')
}

const modalOptions = {
    placement: 'top-center', // 弹框位置
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'searchModalEl',
    override: true
};

// 初始化 flowbit 相关组件
onMounted(() => {
    initCollapses();
    initDropdowns();
    initModals();
    initAccordions();

    // 初始化
    const $modalElement = document.querySelector('#search-modal');
    searchModal.value = new Modal($modalElement, modalOptions, instanceOptions);
    // 初始化知识库目录 Drawer
    const $drawerElement = document.getElementById('wiki-catalog-drawer');
    wikiDrawer.value = new Drawer($drawerElement, drawerOptions, instanceOptionsDrawer);
    // 注册键盘事件监听器
    window.addEventListener('keydown', handleKeyDown);
})

onBeforeUnmount(() => {
    // 在组件销毁前移除事件监听器，防止内存泄漏
    window.removeEventListener('keydown', handleKeyDown);
})

// 点击搜索框
const clickSearchBtn = () => {
    // 显示搜索弹框
    searchModal.value.show()
    // 激活搜索框
    searchInputRef.value.focus()
}

//搜索输入框引用
const searchInputRef = ref(null)

// 键盘监听
const handleKeyDown = (event) => {
    // 检查是否按下了 Ctrl 键和 K 键
    if (event.ctrlKey && event.key === 'k') {
        // 阻止激活浏览器本身的搜索框
        event.preventDefault()
        searchModal.value.show()

        //激活搜索框
        searchInputRef.value.focus()
    }
}

const jumpToArticleDetailPage = (articleId) => {
    searchModal.value.hide()
    router.push('/article/' + articleId)
}

// 跳转文章详情页
const goWikiArticleDetailPage = (articleId) => {
    isDrawerExpand.value = false
    router.push({path: '/wiki/' + route.params.wikiId, query: {articleId}})
}

// 对外暴露属性，将目录数据传进来
const props = defineProps({
    catalogs: {
        type: Array,
        default: []
    }
})

</script>

<style scoped>
.vt-switch {
    --vt-c-divider-light-1: rgba(60, 60, 60, .29);
    --vt-c-divider: var(--vt-c-divider-light-1);
    --vt-c-white-mute: #f1f1f1;
    --vt-c-bg-mute: var(--vt-c-white-mute);
    position: relative;
    border-radius: 11px;
    display: block;
    width: 40px;
    height: 22px;
    flex-shrink: 0;
    border: 1px solid var(--vt-c-divider);
    background-color: var(--vt-c-bg-mute);
    transition: border-color .25s, background-color .25s;
}

.dark .vt-switch {
    --vt-c-white-mute: #f1f1f1;
    background-color: #2f2f2f;
    transition: border-color .25s, background-color .25s;
}

.vt-switch-check {
    --vt-c-white: #ffffff;
    --vt-shadow-1: 0 1px 2px rgba(0, 0, 0, .04), 0 1px 2px rgba(0, 0, 0, .06);
    position: absolute;
    top: 1px;
    left: 1px;
    width: 18px;
    height: 18px;
    border-radius: 50%;
    background-color: var(--vt-c-white);
    box-shadow: var(--vt-shadow-1);
    transition: background-color .25s, transform .25s;
}

.dark .vt-switch-appearance .vt-switch-check {
    transform: translate(18px);
}

.dark .vt-switch-check {
    --vt-c-black: #1a1a1a;
    background-color: var(--vt-c-black);
}

.vt-switch-icon {
    position: relative;
    display: block;
    width: 18px;
    height: 18px;
    border-radius: 50%;
    overflow: hidden;
}

.vt-switch-icon svg {
    position: absolute;
    top: 3px;
    left: 3px;
    width: 12px;
    height: 12px;
    fill: rgba(60, 60, 60, .7);
}

.dark .vt-switch-icon svg {
    fill: rgba(255, 255, 255, .87);
    transition: opacity .25s;
}

.dark .vt-switch-appearance-moon {
    opacity: 1;
}

.vt-switch-appearance-sun {
    opacity: 1;
}

#checkbox {
    display: none;
}

.toggle {
    position: relative;
    cursor: pointer;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    transition-duration: .5s;
    width: 23px;
    height: 23px;
    gap: 5px;
}

.bars {
    width: 100%;
    height: 2.5px;
    background-color: rgb(107 114 128 / 1);
    border-radius: 2.5px;
}

#bar2 {
    transition-duration: .8s;
}

#bar1,
#bar3 {
    width: 70%;
}

#checkbox:checked+.toggle .bars {
    position: absolute;
    transition-duration: .5s;
}

#checkbox:checked+.toggle #bar2 {
    transform: scaleX(0);
    transition-duration: .5s;
}

#checkbox:checked+.toggle #bar1 {
    width: 100%;
    transform: rotate(45deg);
    transition-duration: .5s;
}

#checkbox:checked+.toggle #bar3 {
    width: 100%;
    transform: rotate(-45deg);
    transition-duration: .5s;
}

#checkbox:checked+.toggle {
    transition-duration: .5s;
    transform: rotate(180deg);
}
</style>