<template>
    <!-- 使用 grid 网格布局，并指定列数为 2，高度占满全屏 -->
    <div class="grid grid-cols-2 h-screen">
      <!-- 默认先适配移动端，占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端）；背景色为黑色 -->
      <div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-black">
        <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
        <div class="flex justify-center items-center h-full">
          左边栏
        </div>
      </div>
      <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
        <div class="flex justify-center items-center h-full">
          右边栏
        </div>
      </div>  
    </div>
</template>