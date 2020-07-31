<template>
    <div id="radar"></div>
</template>

<script>
import { Chart } from '@antv/g2'
import DataSet from '@antv/data-set'
import { getCategoryCount } from '@/api/data'

export default {
  name: 'RadarGraph',
  data() {
    return {
      categories: [],
      maxCount: 0
    }
  },
  mounted() {
    this.getRecentlySevenDaysData()
  },
  methods: {
    createRadarGraph() {
      const { DataView } = DataSet;
      const dv = new DataView().source(this.categories);
      dv.transform({
        type: 'fold',
        fields: ['count'], // 展开字段集
        key: 'user', // key字段
        value: 'score' // value字段
      });

      const chart = new Chart({
        container: 'radar',
        autoFit: true,
        height: 500
      });
      chart.data(dv.rows);
      chart.scale('score', {
        min: 0,
        max: this.maxCount
      });
      chart.coordinate('polar', {
        radius: 0.8
      });
      chart.tooltip({
        shared: true,
        showCrosshairs: true,
        crosshairs: {
          line: {
            style: {
              lineDash: [4, 4],
              stroke: '#333'
            }
          }
        }
      });
      chart.axis('name', {
        line: null,
        tickLine: null,
        grid: {
          line: {
            style: {
              lineDash: null
            }
          }
        }
      });
      chart.axis('score', {
        line: null,
        tickLine: null,
        grid: {
          line: {
            type: 'line',
            style: {
              lineDash: null
            }
          }
        }
      })

      chart
        .line()
        .position('name*score')
        .color('user')
        .size(2);
      chart
        .point()
        .position('name*score')
        .color('user')
        .shape('circle')
        .size(4)
        .style({
          stroke: '#fff',
          lineWidth: 1,
          fillOpacity: 1
        });
      chart
        .area()
        .position('name*score')
        .color('user');
      chart.render();
    },
    getRecentlySevenDaysData() {
      getCategoryCount().then(response => {
        if (response && response.data) {
          const data = response.data
          const categories = data.list
          let maxCount = categories[0].count
          categories.forEach(category => {
            maxCount = maxCount >= category.count ? maxCount : category.count
          })
          this.maxCount = maxCount < 10 ? 20 : Math.round(maxCount / 10) * 10
          this.categories = categories
          this.createRadarGraph()
        }
      })
    }
  }
}
</script>