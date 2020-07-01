<template>
  <v-card>
    <v-card-title>TODAY TASKS</v-card-title>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="12" md="8">
        <v-simple-table height="300px">
          <template v-slot:default>
            <tbody v-if="todayList = null">
                <td>업무 없음</td>
            </tbody>
            <tbody v-else>
              <tr v-for="list in todayList" :key="list.name">
                <td>
                  <router-link :to="`/projects/${list.prjId}`"
                    >{{ list.prjTitle }}>{{ list.ptitle }}
                  </router-link>
                </td>
                <td>{{ list.ctitle }}</td>
                <td>{{ list.managerName }}</td>
                <td v-if="list.state == 'p'">
                  <v-chip class="ma-2" small color="blue" text-color="white">
                    진행
                  </v-chip>
                </td>
                <td v-if="list.state == 'w'">
                  <v-chip class="ma-2" small color="yellow">
                    대기
                  </v-chip>
                </td>
                <td v-if="list.state == 'h'">
                  <v-chip class="ma-2" small>
                    보류
                  </v-chip>
                </td>
                <td v-if="list.state == 'e'">
                  <v-chip class="ma-2" small color="red" text-color="white">
                    긴급
                  </v-chip>
                </td>
                <td v-if="list.state == 'c'">
                  <v-chip class="ma-2" small color="green" text-color="white">
                    완료
                  </v-chip>
                </td>
                <td>{{ list.startDate }} ~ {{ list.endDate }}</td>
                <td v-show="list.usePublic"></td>
                <td v-show="!list.usePublic"><v-icon>mdi-lock</v-icon></td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
      <v-col cols="12" md="4">
        <div style="width:50%;margin:0 auto">
          <canvas id="todayChart" width="300" height="300"></canvas>
        </div>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
// import TodayChart from "./TodayChart.vue";
import { mapState, mapActions } from "vuex";
import { chartjs } from "../../utils/todoChart.js";

export default {
  data() {
    return {
      chartStateCnt: { h: 0, p: 0, c: 0, w: 0, e: 0 },
    };
  },
  created() {
    this.FETCH_TODAY_DASHBOARD().then(() => {
      for (var i = 0; i < this.todayList.length; i++) {
        switch (this.todayList[i].state) {
          case "h":
            this.chartStateCnt.h++;
            break;
          case "p":
            this.chartStateCnt.p++;
            break;
          case "c":
            this.chartStateCnt.c++;
            break;
          case "w":
            this.chartStateCnt.w++;
            break;
          case "e":
            this.chartStateCnt.e++;
            break;
        }
      }
      console.log(this.chartStateCnt);
      const chartObj = {
        type: "doughnut",
        data: {
          datasets: [
            {
              data: [
                this.chartStateCnt.h,
                this.chartStateCnt.p,
                this.chartStateCnt.c,
                this.chartStateCnt.w,
                this.chartStateCnt.e,
              ],
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
            text: "TODAY TASKS",
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
      };
      chartjs.createChart("todayChart", chartObj);
    }); // store -> actions
  },
  computed: {
    ...mapState({
      todayList: "todayList",
    }),
  },
  methods: {
    ...mapActions(["FETCH_TODAY_DASHBOARD"]),
  },
};
</script>

<style></style>
