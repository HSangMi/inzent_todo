<template>
  <v-card width="90%" class="mx-auto">
    <v-card-title>STARRED TASKS</v-card-title>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="12" md="8">
        <v-simple-table height="300px" class="mx-10">
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-center">[프로젝트]&nbsp;/업무</th>
                <th class="text-center">업무명</th>
                <th class="text-center">업무 담당자</th>
                <th class="text-center">진행 상태</th>
                <th class="text-center">기간</th>
                <th class="text-center">공개여부</th>
              </tr>
            </thead>
            <tbody v-if="starredList.length == 0">
              <td colspan="6" class="text-center">NO TASKS</td>
            </tbody>
            <tbody v-else>
              <tr v-for="list in starredList" :key="list.name">
                <td>
                  <router-link
                    :to="`/projects/${list.prjId}`"
                  >[{{ list.prjTitle }}]&nbsp;/{{ list.ptitle }}</router-link>
                </td>
                <td class="text-h4">{{ list.ctitle }}</td>
                <td>{{ list.managerName }}</td>
                <td v-if="list.state == 'p'">
                  <v-chip class="ma-2" small color="blue" text-color="white">진행</v-chip>
                </td>
                <td v-if="list.state == 'w'">
                  <v-chip class="ma-2" small color="yellow">대기</v-chip>
                </td>
                <td v-if="list.state == 'h'">
                  <v-chip class="ma-2" small>보류</v-chip>
                </td>
                <td v-if="list.state == 'e'">
                  <v-chip class="ma-2" small color="red" text-color="white">긴급</v-chip>
                </td>
                <td v-if="list.state == 'c'">
                  <v-chip class="ma-2" small color="green" text-color="white">완료</v-chip>
                </td>
                <td>{{ list.startDate }} ~ {{ list.endDate }}</td>
                <td v-show="list.usePublic">
                  <v-icon>mdi-sort-variant</v-icon>
                </td>
                <td v-show="!list.usePublic">
                  <v-icon>mdi-sort-variant-lock</v-icon>
                </td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
      <v-divider class="mx-4" vertical></v-divider>
      <v-col cols="12" md="3">
        <p class="text-center my-3">STARRED TASKS</p>
        <v-divider class="my-3"></v-divider>
        <div v-if="starredList.length == 0">
          <p class="text-center">NO TASKS</p>
        </div>
        <div class="mx-15" v-else>
          <canvas id="starredChart" width="300" height="300"></canvas>
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
      chartStateCnt: { h: 0, p: 0, c: 0, w: 0, e: 0 }
    };
  },
  created() {
    this.FETCH_STARRED_DASHBOARD().then(() => {
      for (var i = 0; i < this.starredList.length; i++) {
        switch (this.starredList[i].state) {
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
                this.chartStateCnt.e
              ],
              backgroundColor: [
                "#BFC9CA",
                "#5DADE2",
                "#82E0AA",
                "#F7DC6F",
                "#F1948A"
              ],
              borderAlign: "left"
            }
          ],
          // These labels appear in the legend and in the tooltips when hovering different arcs
          labels: ["보류", "진행", "완료", "대기", "긴급"]
        },
        options: {
          legend: {
            position: "right",
            verticalAlign: "right"
          },
          responsive: false
        },
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true
              }
            }
          ]
        }
      };
      chartjs.createChart("starredChart", chartObj);
    }); // store -> actions
  },
  computed: {
    ...mapState({
      starredList: "starredList"
    })
  },
  methods: {
    ...mapActions(["FETCH_STARRED_DASHBOARD"])
  }
};
</script>

<style></style>
