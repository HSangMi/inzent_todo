<template>
  <div style="width:50%;margin:0 auto">
    <canvas id="interestChart" width="300" height="300"></canvas>
  </div>
</template>

<script>
import { chartjs } from "../../utils/todoChart.js";
export default {
  data() {
    return {};
  },
  created() {
    // this.fetchData();
  },
  mounted() {
    chartjs.createChart("interestChart", {
      type: "doughnut",
      data: {
        datasets: [
          {
            data: [10, 20, 30, 40, 50],
            backgroundColor: [
              "#BFC9CA",
              "#5DADE2",
              "#82E0AA",
              "#F7DC6F",
              "#F1948A",
            ],
            borderAlign: "left",
          },
        ],

        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: ["보류", "진행", "완료", "대기", "긴급"],
      },
      options: {
        title: {
          display: true,
          text: "INTEREST TASKS",
          position: "top",
        },
        legend: {
          position: "right",
          verticalAlign: "right",
        },
        responsive: false,
      },
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true,
            },
          },
        ],
      },
    });
  },
  methods: {},
};
</script>

<style></style>
