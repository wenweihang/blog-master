<template>
  <div id="log">
    <div class="header-container">
      <h1>日志管理</h1>
      <a-form-model>
        <a-form-model-item>
          <a-input-group compact>
            <a-input style="width: 60px" default-value="等级" :disabled="true" />
            <a-select v-model="selectLevel" style="width: 100px" @change="handleLevelChange">
              <a-select-option v-for="level in levelData" :key="level">
                {{ level }}
              </a-select-option>
            </a-select>
          </a-input-group>
          <a-input-group compact>
            <a-input style="width: 60px" default-value="日期" :disabled="true"/>
            <a-date-picker style="width: 160px" @change="handleDateChange"/>
          </a-input-group>
        </a-form-model-item>
      </a-form-model>
    </div>
    <div class="content-container">
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="logData"
        :loading="loading"
        :pagination="pagination"
        :scroll="{ x: 1300 }"
      >
        <template slot="level" slot-scope="text, record">
          <a-tag color="blue" v-if="record.level == 'INFO'">{{ record.level }}</a-tag>
          <a-tag color="pink" v-else-if="record.level == 'WARN'">{{ record.level }}</a-tag>
          <a-tag color="red" v-else>{{ record.level }}</a-tag>
        </template>
        <template slot="action" slot-scope="text, record">
          <a @click="onLookDetail(record)">查看</a>
        </template>
      </a-table>
    </div>
    <!-- 查看日志详细 -->
    <a-modal
      v-model="logDetailVisible"
      title="查看日志"
      :footer="null"
    >
      <ul class="log-detail">
        <li>
          <span class="label">调用者：</span>
          <span class="content">{{ currentLog.username }}</span>
        </li>
        <li>
          <span class="label">IP地址：</span>
          <span class="content">{{ currentLog.ip }}</span>
        </li>
        <li>
          <span class="label">等级：</span>
          <span class="content">
            <a-tag color="blue" v-if="currentLog.level == 'INFO'">{{ currentLog.level }}</a-tag>
            <a-tag color="pink" v-else-if="currentLog.level == 'WARN'">{{ currentLog.level }}</a-tag>
            <a-tag color="red" v-else>{{ currentLog.level }}</a-tag>
          </span>
        </li>
        <li>
          <span class="label">参数：</span>
          <span class="content">{{ currentLog.params }}</span>
        </li>
        <li>
          <span class="label">创建时间：</span>
          <span class="content">{{ currentLog.newCreateTime }}</span>
        </li>
        <li>
          <span class="label">进程：</span>
          <span class="content">{{ currentLog.thread }}</span>
        </li>
        <li>
          <span class="label">消息：</span>
          <span class="content">{{ currentLog.message }}</span>
        </li>
        <li>
          <span class="label">位置：</span>
          <span class="content">{{ currentLog.location }}</span>
        </li>
      </ul>
      <div class="close">
        <a-button style="width: 200px" @click="onCloseBtn">关闭</a-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import { getLogs, getLevels } from '@/api/log'
import { formatDate } from '@/utils/date'

const columns = [
  {
    title: '序号',
    dataIndex: 'id',
    width: '60px',
    align: 'center'
  },
  {
    title: '调用者',
    dataIndex: 'username',
    width: '160px'
  },
  {
    title: 'IP地址',
    dataIndex: 'ip',
    width: '160px'
  },
  {
    title: '消息',
    dataIndex: 'message',
    width: '200px',
    ellipsis: true
  },
  {
    title: '参数',
    dataIndex: 'params',
    width: '240px',
    ellipsis: true
  },
  {
    title: '等级',
    dataIndex: 'level',
    width: '90px',
    scopedSlots: { customRender: 'level' }
  },
  {
    title: '调用时间',
    dataIndex: 'newCreateTime',
    width: '200px'
  },
  {
    title: '调用位置',
    dataIndex: 'location',
    width: '240px',
    ellipsis: true
  },
  {
    title: '操作',
    key: 'action',
    width: '100px',
    scopedSlots: { customRender: 'action' }
  }
]
export default {
  name: 'Log',
  mounted() {
    this.getLogData()
    this.getLevelData()
  },
  data() {
    return {
      logData: [],
      levelData: [],
      loading: false,
      columns,
      pagination: {
        // 分页属性
        current: 1, // 当前页数
        total: 0,
        pageSize: 8,
        showTotal: total => `共有${total}条数据`,
        onChange: this.onPageChange.bind(this) // 点击页码触发
      },
      selectLevel: 'ALL',
      selectDay: '',
      logDetailVisible: false,
      currentLog: {}
    }
  },
  methods: {
    getLogData() {
      this.loading = true
      getLogs(
        this.selectLevel,
        this.selectDay,
        this.pagination.current,
        this.pagination.pageSize
      ).then(
        response => {
          if (response && response.data) {
            const data = response.data
            const logs = data.list
            logs.forEach(log => {
              log.newCreateTime = formatDate(log.createDate)
            })
            // 更新分页的数据总数
            this.pagination.total = data.total
            this.logData = logs
            this.loading = false
          }
        }
      )
    },
    getLevelData() {
      getLevels().then(response => {
        if (response && response.data) {
          const data = response.data
          const levels = data.list
          levels.unshift('ALL')
          this.levelData = levels
        }
      })
    },
    onPageChange(currentPage) {
      this.pagination.current = currentPage
      this.getLogData()
    },
    onLookDetail(record) {
      this.logDetailVisible = true
      this.currentLog = record
    },
    handleLevelChange(value) {
      this.pagination.current = 1
      this.getLogData()
    },
    handleDateChange(date, dateString) {
      this.selectDay = dateString
      this.pagination.current = 1
      this.getLogData()
    },
    onCloseBtn() {
      this.logDetailVisible = false
    }
  }
}
</script>

<style scoped lang="scss">
.ant-form-item-children {
  display: flex !important;
}
.ant-input-group {
  width: auto !important;
}
.ant-input-group:last-child {
  margin-left: 16px;
}
.log-detail {
  margin-bottom: 24px;
  li {
    display: flex;
    height: auto;
    padding: 8px 0;
  }
  .label {
    display: inline-block;
    width: 80px;
    color: rgba(0, 0, 0, .85);
    text-align: right;
  }
  .content {
    display: inline-block;
    width: 380px;
    height: auto;
    color: rgba(0, 0, 0, .65);
  }
}
.close {
  padding: 12px;
  text-align: center;
}
</style>
