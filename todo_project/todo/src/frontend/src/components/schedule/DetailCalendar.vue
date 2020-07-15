<template>
  <v-dialog
    persistent
    max-width="600px"
    v-model="isDetailCalendar"
    :close-on-content-click="false"
    offset-x
  >
    <v-card>
      <v-card-title>
        <span class="headline">DETAILS TASKS</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-divider></v-divider>
          <template v-if="clickDateList">
            <!-- <v-list
              subheader
              three-line
              v-for="item in clickDateList"
              :key="item.ctitle"
              @click="true"
            >-->
            <v-list three-line>
              <v-list-item-group multiple active-class="pink--text">
                <template v-for="(item, index) in clickDateList">
                  <v-list-item :key="item.cId">
                    <v-list-item-content>
                      <v-list-item-title class="text-h5" v-text="item.ctitle"></v-list-item-title>
                      <v-spacer></v-spacer>
                      <v-list-item-subtitle v-if="item.imgCode!=null">
                        <v-tooltip
                          bottom
                          color="#5a5a5a"
                          v-for="(mem, index) in item.managerName"
                          :key="index"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-avatar v-bind="attrs" v-on="on" size="20px">
                              <img
                                class="user-avatar"
                                :src="'data:image;base64,'+ item.imgCode[index]"
                              />
                            </v-avatar>
                          </template>
                          <span font-color="white" class="user-font">{{mem}}</span>
                        </v-tooltip>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.imgCode==null">
                        <span class="user-font my-5">No Member</span>
                      </v-list-item-subtitle>
                      <v-spacer></v-spacer>
                      <v-list-item-subtitle>
                        <span
                          class="detail-font"
                        >{{item.prjTitle+' / '+item.ptitle+' / '+item.ctitle}}</span>
                      </v-list-item-subtitle>
                    </v-list-item-content>
                    <v-list-item-action>
                      <v-chip
                        v-if="item.cstate == 'P' "
                        class="text-center ma-2 my-5"
                        x-small
                        color="blue"
                        text-color="white"
                      >진행</v-chip>
                      <v-chip
                        v-if="item.cstate == 'W'"
                        class="text-center ma-2 my-5"
                        x-small
                        color="yellow"
                      >대기</v-chip>
                      <v-chip v-if="item.cstate == 'H' " class="text-center ma-2" small>보류</v-chip>
                      <v-chip
                        v-if="item.cstate == 'E'"
                        class="text-center ma-2 my-5"
                        x-small
                        color="red"
                        text-color="white"
                      >긴급</v-chip>
                      <v-chip
                        v-if="item.cstate == 'C'"
                        class="text-center ma-2 my-5"
                        x-small
                        color="green"
                        text-color="white"
                      >완료</v-chip>
                    </v-list-item-action>
                    <v-list-item-action>
                      <v-icon
                        class="my-5"
                        small
                        v-show="item.cusePublic"
                      >mdi-lock-open-variant-outline</v-icon>
                      <v-icon class="my-5" small v-show="!item.cusePublic">mdi-lock-outline</v-icon>
                    </v-list-item-action>
                  </v-list-item>
                  <v-divider v-if="index + 1 < clickDateList.length" :key="index"></v-divider>
                </template>
              </v-list-item-group>
            </v-list>
            <!-- <v-list-item-avatar></v-list-item-avatar> -->
          </template>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="SET_IS_DETAIL_CALENDAR(false)">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
  data: () => ({
    dateList: ""
  }),
  created() {},
  computed: {
    ...mapState({
      clickDateList: "getClickDateList"
    }),
    ...mapState(["isDetailCalendar"])
  },
  methods: {
    ...mapMutations(["SET_IS_DETAIL_CALENDAR"])
  }
};
</script>

<style scoped>
.detail-font {
  font-size: 11px;
  color: cadetblue;
}
.user-font {
  font-size: 11px;
}
.user-list {
  padding-left: 15px;
}
.user-avatar {
  margin-left: -5px;
}
</style>
