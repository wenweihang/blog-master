<template>
  <div class="filter-box">
    <span class="filter-type">排序:</span>
    <span class="select-type">默认</span>
  </div>
</template>

<style lang="scss" scoped>
.filter-box {
  height: 38px;
  padding: 0 24px;
  margin-bottom: 8px;
  line-height: 38px;
  background: #fff;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
}

.filter-type {
  margin-right: 8px;
  color:rgba(0, 0, 0, 0.25)
}

.filter-type ~ span {
  margin: 0 4px;
  color:rgba(0, 0, 0, 0.55)
}

.filter-type ~ span:hover {
  color: #1890ff;
  cursor: pointer;
}

.filter-box .select-type {
  color: #1890ff;
}
</style>
