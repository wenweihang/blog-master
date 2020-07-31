<template>
  <div id="area"></div>
</template>

<script>
import { Chart } from '@antv/g2'
import { getRecentlySevenDays } from '@/api/data'
import { formatDay } from '@/utils/date'

export default {
  name: 'AreaGraph',
  data() {
    return {
      dates: [],
      maxUv: 0
    }
  },
  mounted() {
    this.getRecentlySevenDaysData()
  },
  methods: {
    createAreaGraph() {
      const chart = new Chart({
        container: 'area',
        autoFit: true,
        height: 500
      })
      chart.data(this.dates)
      chart.scale({
        uv: {
          min: 0,
          max: this.maxUv,
          nice: true
        },
        newDateTime: {
          type: 'time'
        }
      })
      chart.tooltip({
        showCrosshairs: true,
        shared: true
      })

      chart.area().position('newDateTime*uv')
      chart.line().position('newDateTime*uv')
      chart.render()
    },
    getRecentlySevenDaysData() {
      getRecentlySevenDays().then(response => {
        if (response && response.data) {
          const data = response.data
          const dates = data.list
          let maxUv = dates[0].uv
          dates.forEach(date => {
            date.newDateTime = formatDay(date.dateTime)
            maxUv = maxUv >= date.uv ? maxUv : date.uv
          })
          this.maxUv = maxUv < 10 ? 20 : Math.round(maxUv / 10) * 10
          this.dates = dates
          this.createAreaGraph()
        }
      })
    }
  }
}
</script>