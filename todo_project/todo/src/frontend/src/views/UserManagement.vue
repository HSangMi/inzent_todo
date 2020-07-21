<template>
<v-container fluid>
<v-row style="padding-right:60px">
    <v-col cols="2">
    <v-treeview
        v-model="selection"
        :items="items"
        selectable
        selection-type="leaf"
        return-object
        open-all
        open-on-click
        @input="selectDept"
    ></v-treeview>
    </v-col>
    <v-divider vertical></v-divider>
    <v-col>
    <v-data-table
        :headers="headers"
        :items="this.userList"
        :search="search"
        v-model="selected"
        show-select
    >
        <template v-slot:item.avatar="{ item }">
            <v-avatar size="32px" v-if="item.imgCode">
                <img :src="item.imgCode">
            </v-avatar>
            <v-avatar size="32px" v-else>
                <v-icon>mdi-account</v-icon>
            </v-avatar>
        </template> 
        <template v-slot:top>
        <v-toolbar flat color="white">
            <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Search"
                single-line
                hide-details
            ></v-text-field>
            <v-spacer></v-spacer>

            <!-- 유저 추가 모달 -->
            <v-dialog v-model="dialog" width="1000">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn 
                        color="primary"
                        dark
                        class="mb-2"
                        v-bind="attrs"
                        v-on="on"
                        icon
                        >
                        <v-icon>mdi-account-plus</v-icon>
                    </v-btn>
                </template>
                
                <v-card width="auto">
                    <v-card-title class="headline grey lighten-2" primary-title>
                        <span class="headline">{{ formTitle }}</span>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" icon @click="close">
                            <v-icon color="black lighten-1">mdi-close</v-icon>
                        </v-btn>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row> 
                            <v-col cols="12" sm="6" md="3">
                                <v-text-field outlined v-model="editedItem.id" label="사용자 ID"></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="3">
                                <v-text-field outlined v-model="editedItem.name" label="이름"></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="3">
                                <v-select
                                    v-model="editedItem.rank"
                                    :items="ranks"
                                    label="직급"
                                    outlined
                                />
                            </v-col>
                            <v-col cols="12" sm="6" md="3">
                                <v-select
                                    v-model="editedItem.deptName"
                                    :items="deptList"
                                    item-text="deptName"
                                    label="부서"                                    
                                    outlined
                                />
                            </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="save">확인</v-btn>
                        <v-btn color="blue darken-1" text @click="close">취소</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-toolbar>
        </template>
        <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="editItem(item)"
        >
            mdi-pencil
        </v-icon>
        <v-icon
            small
            @click="deleteItem(item)"
        >
            mdi-delete
        </v-icon>
        </template>
    </v-data-table>
    </v-col>
</v-row>
</v-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex';

export default {
    data: () => ({
        dialog: false,
        selection: [],
        items: [],
        search: '',
        selected: [],
        headers: [
            {
                text: '사진',
                align: 'start',
                sortable: false,
                value: 'avatar',
            },
            { text: '이름', value: 'name' },
            { text: '부서', value: 'deptName' },
            { text: '직급', value: 'rank' },
            { text: '연락처', value: 'phone' },
            { text: '이메일', value: 'email' },
            { text: 'Actions', value: 'actions', sortable: false },
        ],
        model: null, 
        myTree: '',
        editedIndex: -1,
        editedItem: {
            id: '',
            name: '',
            rank: '',
            deptName: ''
        },
        defaultItem: {        
            id: '',
            name: '',
            rank: '',
            deptName: ''
        },
        ranks: ["부장", "과장", "차장", "대리", "사원"],    
    }),
    created() {    
        this.fatch_data()
        // this.initialize()
        this.SET_HEADER_TITLE("USER MANAGEMENT");
        console.log("==========")
    },
    computed: {
        ...mapState({
            deptList: "deptList",
            userList: "userList"
        }),
        formTitle () {
            return this.editedIndex === -1 ? '사용자 등록' : '사용자 수정'
        },
    },

    watch: {
        dialog (val) {
            val || this.close()
        },
    },

    methods: {
        ...mapActions(["FATCH_DEPTLIST", "FATCH_USERLIST"]),
        ...mapMutations(["SET_HEADER_TITLE"]),

        fatch_data() {
            this.FATCH_DEPTLIST().then(() => {
                let list = this.deptList

                console.log("list", list)
                list.forEach(item => {
                    for(let i=0; i<list.length; i++) {
                        if(item.parentDeptCode ===list[i].deptCode) {
                            item.parentId = list[i].id
                            break;
                        }
                    }
                })
                console.log("foreach list", list)

                let myTree = null
                list.forEach(item => {
                    if(myTree === null) {
                        myTree={id: item.id, name: item.deptName}
                        console.log(';',myTree)
                    } else {
                        this.Recursive(item, myTree)
                    }
                })
                // console.log("myTree", myTree)

                let treeArr = [];
                treeArr.push(myTree);

                this.items = treeArr;
                this.myTree = myTree;
            });
        },
        Recursive(item,tree) {
            //tree Id 와 item parentId가 같다면 
            if(tree.id === item.parentId) {
                if(typeof tree.children === "undefined") { // tree에 children이 없다면
                    tree.children = []
                    tree.children.push({id : item.id, name : item.deptName})
                }else { // tree에 children이 있다.
                    tree.children.push({id : item.id, name : item.deptName})
                }
            }else { //tree Id와 item parentId가 다름
                if(typeof tree.children === "undefined") { // tree에 children이 없다면
                    return ;
                } else { // tree에 children이 있다면
                    tree.children.forEach(child => {
                        this.Recursive(item,child)
                    })
                }
            }
        },

        selectDept(){
            const selectedDept = []
            for(let i=0; i<this.selection.length; i++) {
                var dptmt = this.deptList.find(item =>{
                    return item.id == this.selection[i].id
                })
                selectedDept.push(dptmt.deptCode)
            }

            console.log("myTree", this.myTree)
            console.log("chlidren id " + this.myTree.children[0].id)
            console.log(selectedDept)

            this.FATCH_USERLIST(selectedDept)
        },

        editItem(item) {
            this.editedIndex = this.userList.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem(item) {
            const index = this.userList.indexOf(item)
            confirm('Are you sure you want to delete this item?') && this.userList.splice(index, 1)
        },

        close() {
            this.dialog = false
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            })
        },

        save() {
            if (this.editedIndex > -1) {
                Object.assign(this.userList[this.editedIndex], this.editedItem)
            } else {
                console.log(this.editedItem)
                this.userList.push(this.editedItem)
            }
            console.log("aaa", this.userList)
            // this.INSERT_USER(this.editedItem)
            this.close()
        },
    },
}
</script>

<style>
.v-treeview-node__root {
  min-height: 35px;
}
</style>
