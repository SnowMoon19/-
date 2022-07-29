<template>
  <div>
    <div class="header">
      <el-form :inline="true" :model="searchInline" class="demo-form-inline search">
        <el-form-item label="年份">
          <el-date-picker v-model="searchInline.year" type="year" placeholder="选择年"/>
        </el-form-item>
        <!--        <el-form-item label="任务版">-->
        <!--          <el-input v-model="searchInline.type" placeholder="输入任务版名称"/>-->
        <!--        </el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="getTaskTypeByQuery">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="body">
      <div class="year_title">
        <label class="tittle-text">{{ year }}</label>
      </div>
      <div class="myself">
        <div class="card_tittle">
          My Board
        </div>
        <el-card class="task-board-myself">
          <div>
            <div class="show-task">
              <!--该年的所有任务板-->
              <el-button v-for="taskType in taskTypeList" :style="randomRgb()" :key="taskType.typeId" class="task_type_btn" @click="goToTask(taskType.typeId)">
                {{ dealTypeName(taskType.type) }}
              </el-button>
              <el-button class="task_type_btn" style="width: 150px; color: rgba(0,1,2,0.77); background: #1482f0" @click="dialogFormVisible=true">
                新建
              </el-button>
              <!--用嵌套表单新建任务板-->
              <el-dialog :visible.sync="dialogFormVisible" title="新建任务板" width="500px" append-to-body>
                <el-form :inline="true">
                  <el-form-item label="任务名称" style="margin-top: 20px">
                    <el-input v-model="taskType.type" style="width: 320px;" autocomplete="off"/>
                  </el-form-item>
                </el-form>
                <div slot="footer">
                  <el-button @click="dialogFormVisible = false">取 消</el-button>
                  <el-button type="primary" @click="addTaskType()">确 定</el-button>
                </div>
              </el-dialog>
            </div>
          </div>
        </el-card>
      </div>
      <el-divider style="width: 20px;font-size: 20px"><i class="el-icon-cherry" style="font-size: 20px; color: red"/></el-divider>
      <div class="another">
        <div class="card_tittle">
          Another Board
        </div>
        <el-card class="task-board-another">
          <div>
            <div v-if="anotherId !== 'null'" class="show-task">
              <el-button v-for="taskType in anotherTaskTypeList" :style="randomRgb()" :key="taskType.typeId" type="primary" class="task_type_btn" @click="goToTask(taskType.typeId)">
                {{ dealTypeName(taskType.type) }}
              </el-button>
            </div>
            <div v-else>
              你还没有添加对象哦，快去添加吧
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import { getTaskTypeList, updateTaskType } from '../../api/module/event/task'
import { getAnotherId, getId } from '../../utils/auth'
import { getNowFormatTime } from '../../utils/dateTime'
import { isNull } from '../../utils/validate'

export default {
  data() {
    return {
      id: getId(),
      anotherId: getAnotherId(),
      searchInline: {
        year: '2022'
      },
      year: '2022',
      taskTypeList: [],
      anotherTaskTypeList: [],
      // 新建任务板时的临时变量
      taskType: {},
      dialogFormVisible: false,
      // 随机色彩列表
      randomRgb(item) {
        const R = Math.floor(Math.random() * 130)
        const G = Math.floor(Math.random() * 130)
        const B = Math.floor(Math.random() * 130)
        return {
          background: 'rgb(' + R + ',' + G + ',' + B + ', .5)'
        }
      }
    }
  },
  created() {
    this.getTaskType(this.id, this.searchInline.year)
    if (!isNull(this.anotherId)) this.getTaskType(this.anotherId, this.searchInline.year) // 有另一半id才请求
  },
  methods: {
    getTaskType(id, year) {
      getTaskTypeList(id, year).then(response => {
        if (id === this.id) this.taskTypeList = response.data
        else if (id === this.anotherId) this.anotherTaskTypeList = response.data
        this.year = year
        this.searchInline.year = this.searchInline.year.toString()
      }).catch(error => {
        console.log(error)
      })
    },
    /**
     * 年份查询
     */
    getTaskTypeByQuery() {
      this.searchInline.year = new Date(this.searchInline.year).getFullYear()
      if (isNaN(this.searchInline.year) || this.searchInline.year === 1970) {
        this.searchInline.year = '2022'
      }
      this.getTaskType(this.id, this.searchInline.year)
      if (!isNull(this.anotherId)) this.getTaskType(this.anotherId, this.searchInline.year) // 有另一半id才请求
    },
    /** 任务板名称超出8个字母.. **/
    dealTypeName(typeName) {
      // 如果无名称处理一下
      if (typeName === null) return 'null'
      let name = typeName
      let len = name.length
      for (let i = 0; i < name.length; ++i) {
        if (name.charCodeAt(i) > 255) ++len
      }
      if (len > 8) {
        name = name.substring(0, 4)
        name = name + '..'
      }
      return name
    },
    /** 新建一个任务板 **/
    addTaskType() {
      this.taskType.createTime = getNowFormatTime()
      this.taskType.id = getId()
      this.taskType.isDeleted = 0
      this.taskType.year = this.year
      updateTaskType(this.taskType).then(response => {
        // 新建之后关闭对话框
        this.dialogFormVisible = false
        // 重新加载任务板列表
        this.getTaskType(this.id, this.searchInline.year)
        // 临时任务板清除
        this.taskType.type = ''
      }).catch(error => {
        console.log(error)
      })
    },
    /**
     * 进入任务板
     */
    goToTask(typeId) {
      this.$router.push({ path: '/event/task/board', query: { typeId: typeId }})
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
/**
  设置滚动条样式
 */
::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 40px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 8px;
}
::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(97, 184, 179, 0.1);
  background: white;
}
::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  box-shadow: inset 0 0 5px rgba(87, 175, 187, 0.1);
  border-radius: 10px;
  //background: #a0e6e3;
}
.header {
  height: 80px;
  .search {
    background: rgba(180,180,180,0.36);
    display: inline-block;
    padding-top: 20px;
    padding-left: 60px;
    margin-bottom: 20px;
    width: 100%;
  }
}

.year_title {
  height: 80px;
  width: 100%;
  text-align: center;
  margin-top: 25px;
  .tittle-text {
    font-family: "Arial";
    color: #000000;
    font-size: 50px;
    margin: auto;
  }
}

.task-board-myself {
  margin: 0 50px 50px 50px;
  height: 200px;
  background: #52aeff;
  border-radius: 25px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0.8)
}

.task-board-another {
  margin: 0 50px 50px 50px;
  height: 200px;
  background: rgba(230,187,217,0.95);
  border-radius: 25px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0.8)
}

.show-task {
  overflow-x: auto;
  white-space: nowrap;
  height: 180px;
}

.task_type_btn {
  margin-top: 8px;
  margin-right: 30px;
  border-radius: 20px;
  color: cornsilk;
  border-left-color: white;
  border-right-color: white;
  border-width: 5px;
  border-top-color: white;
  border-bottom-color: white;
  height: 140px;
  width: 200px;
  font-size: 30px;
  box-shadow: 0 0 5px rgba(255, 255, 255, 20);
  &:hover {
    background: rgba(180,174,178,0.95) !important; // 子模块的hover背景色
  }
}

.card_tittle {
  background: #1482f0;
  color: white;
  font-size: 20px;
  margin-left: 75px;
  margin-top: 15px;
  border-radius: 10px 10px 0 0;
  width: 200px;
  height: 40px;
  text-align: center;
  padding-top: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0.8)
}

</style>
