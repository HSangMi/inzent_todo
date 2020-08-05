<template>
  <v-card width="95%" class="mx-auto" outlined>
    <v-card-title class="text-h5">
      <v-icon color="yellow">mdi-star</v-icon> 관심 업무
    </v-card-title>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="12" md="9">
        <v-data-table
          :headers="headers"
          :items="listItem"
          :items-per-page="5"
          item-key="pid"
          class="elevation-1"
          height="250"
          no-data-text="관심 업무가 존재하지 않습니다."
        >
          <!-- 공개여부 아이콘 설정 -->
          <template v-slot:item.usePublic="{item}">
            <v-icon small v-show="item.usePublic">mdi-lock-open-variant-outline</v-icon>
            <v-icon small v-show="!item.usePublic">mdi-lock-outline</v-icon>
          </template>
          <!-- 진행상태 표시 -->
          <template v-slot:item.state="{ item }">
            <v-chip v-if="item.state == 'P'" small color="blue" text-color="white">진행</v-chip>
            <v-chip v-if="item.state == 'W'" small color="yellow">대기</v-chip>
            <v-chip v-if="item.state == 'H'" small>보류</v-chip>
            <v-chip v-if="item.state == 'E'" small color="red" text-color="white">긴급</v-chip>
            <v-chip v-if="item.state == 'C'" small color="green" text-color="white">완료</v-chip>
          </template>
        </v-data-table>
      </v-col>
      <v-divider class="mx-4" vertical></v-divider>
      <v-col cols="12" md="2" class="mx-0 auto">
        <div v-if="starredList.length == 0" class="text-center py-5">관심 업무 차트가 존재하지 않습니다.</div>
        <div class="mx-5" v-else>
          <canvas id="starredChart" width="300" height="300"></canvas>
        </div>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { chartjs } from "../../utils/todoChart.js";

export default {
  data() {
    return {
      expanded: [],
      singleExpand: true,
      headers: [
        { text: "업무 명", value: "ptitle", align: "center", width: "20%" },
        {
          text: "프로젝트",
          align: "center",
          sortable: false,
          value: "prjTitle",
          width: "30%",
        },
        { text: "기간", value: "dueDate", align: "center", width: "20%" },
        { text: "진행 상태", value: "state", align: "center", width: "20%" },
        {
          text: "공개 여부",
          value: "usePublic",
          align: "center",
          width: "10%",
        },
      ],
      listItem: [],
      //////////////////////// 차트 ////////////////////////////
      chartStateCnt: { H: 0, P: 0, C: 0, W: 0, E: 0 },
    };
  },
  created() {
    this.FETCH_STARRED_DASHBOARD().then(() => {
      console.log("====", this.starredList);
      for (var i = 0; i < this.starredList.length; i++) {
        this.listItem.push(this.starredList[i]);
        switch (this.starredList[i].state) {
          case "H":
            this.chartStateCnt.H++;
            break;
          case "P":
            this.chartStateCnt.P++;
            break;
          case "C":
            this.chartStateCnt.C++;
            break;
          case "W":
            this.chartStateCnt.W++;
            break;
          case "E":
            this.chartStateCnt.E++;
            break;
        }
      } // end for

      const chartObj = {
        type: "doughnut",
        data: {
          datasets: [
            {
              data: [
                this.chartStateCnt.H,
                this.chartStateCnt.P,
                this.chartStateCnt.C,
                this.chartStateCnt.W,
                this.chartStateCnt.E,
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
      chartjs.createChart("starredChart", chartObj);
    }); // store -> actions
  },
  computed: {
    ...mapState({
      starredList: "starredList",
      starredSub: "starredSub",
    }),
  },
  methods: {
    ...mapActions(["FETCH_STARRED_DASHBOARD"]),
  },
};
</script>

<style scoped>
.sub {
  font-size: 12px;
}
</style>
