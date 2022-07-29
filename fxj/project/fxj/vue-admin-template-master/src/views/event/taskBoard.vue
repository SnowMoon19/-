<template>
  <div class="board">
    <el-col :span="6">
      <el-card class="total_task">
        <div class="task_tittle">
          Total Task
        </div>
        <!--total列表无法拖拽-->
        <!--拖拽列表的元素，对应循环-->
        <el-card v-for="totalTask in totalTaskList" :key="totalTask.num" class="total_task_item">
          <div class="total_task_item_msg">
            Deadline:   {{ dealTime(totalTask.endTime) }} <!-- 截止时间-->
          </div>
          <div class="total_task_item_content">
            {{ totalTask.content }}
          </div>
          <el-link type="primary" class="el-icon-edit task_edit" @click="dialogFormVisible=true;task_cp=totalTask">Edit</el-link>
        </el-card>
        <!--添加按键-->
        <div class="add_button" @click="addTaskVisible=true; task_cp.endTime=null; task_cp.content=null; addTaskType='total'">
          <i class="el-icon-plus"/>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="todo_task">
        <div class="task_tittle">
          Todo
        </div>
        <!--拖拽列表-->
        <draggable v-model="todoTaskList" filter=".add_button" class="dragList" group="task" animation="300" drag-class="dragClass" ghost-class="ghostClass" chosen-class="chosenClass" @start="onStart" @end="onEnd" >
          <!--拖拽列表的元素，对应循环-->
          <el-card v-for="todoTask in todoTaskList" :key="todoTask.num" class="todo_task_item">
            <div class="todo_task_item_msg">
              Deadline: {{ dealTime(todoTask.endTime) }} <!-- 截止时间-->
            </div>
            <div class="todo_task_item_content">
              {{ todoTask.content }}
            </div>
            <el-link type="primary" class="el-icon-edit task_edit" @click="dialogFormVisible=true;task_cp=todoTask">Edit</el-link>
            <!--用嵌套表单修改任务块-->
          </el-card>
          <div class="add_button" @click="addTaskVisible=true; task_cp.endTime=null; task_cp.content=null; addTaskType='todo'">
            <i class="el-icon-plus"/>
          </div>
        </draggable>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="doing_task">
        <div class="task_tittle">
          Doing
        </div>
        <!--拖拽列表-->
        <draggable v-model="doingTaskList" filter=".add_button" class="dragList" group="task" animation="300" drag-class="dragClass" ghost-class="ghostClass" chosen-class="chosenClass" @start="onStart" @end="onEnd" >
          <!--拖拽列表的元素，对应循环-->
          <el-card v-for="doingTask in doingTaskList" :key="doingTask.num" class="doing_task_item">
            <div class="doing_task_item_msg">
              Deadline:   {{ dealTime(doingTask.endTime) }} <!-- 截止时间-->
            </div>
            <div class="doing_task_item_content">
              {{ doingTask.content }}
            </div>
            <el-link type="primary" class="el-icon-edit task_edit" @click="dialogFormVisible=true; task_cp=doingTask">Edit</el-link>
          </el-card>
          <div class="add_button" @click="addTaskVisible=true; task_cp.endTime=null; task_cp.content=null; addTaskType='doing'">
            <i class="el-icon-plus"/>
          </div>
        </draggable>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="done_task">
        <div class="task_tittle">
          Done
        </div>
        <!--拖拽列表-->
        <draggable v-model="doneTaskList" filter=".add_button" class="dragList" group="task" animation="300" drag-class="dragClass" ghost-class="ghostClass" chosen-class="chosenClass" @start="onStart" @end="onEnd" >
          <el-card v-for="doneTask in doneTaskList" :key="doneTask.num" class="done_task_item">
            <div class="done_task_item_msg">
              Deadline:   {{ dealTime(doneTask.endTime) }} <!-- 截止时间-->
            </div>
            <div class="done_task_item_content">
              {{ doneTask.content }}
            </div>
            <el-link type="primary" class="el-icon-edit task_edit" @click="dialogFormVisible=true; task_cp=doneTask">Edit</el-link>
          </el-card>
          <div class="add_button" @click="addTaskVisible=true; task_cp.endTime=null; task_cp.content=null; addTaskType='done'">
            <i class="el-icon-plus"/>
          </div>
        </draggable>
      </el-card>
    </el-col>
    <el-dialog :visible.sync="dialogFormVisible" title="修改" width="600px">
      <el-form>
        <el-form-item label="截止时间" label-width="20%">
          <el-date-picker v-if="task_cp.creator.toString()===id" v-model="task_cp.endTime" type="datetime" placeholder="选择日期时间" style="width: 90%;"/>
          <el-date-picker v-else v-model="task_cp.endTime" type="datetime" placeholder="选择日期时间" style="width: 90%;" disabled/>
        </el-form-item>
        <el-form-item label="创建者" label-width="20%">
          <el-input v-if="task_cp.creator.toString()===id" :value="name" type="text" disabled style="width: 90%;"/>
          <el-input v-else :value="anotherUserName" type="text" disabled style="width: 90%;"/>
        </el-form-item>
        <el-form-item label="任务描述" label-width="20%">
          <el-input v-if="task_cp.creator.toString()===id" v-model="task_cp.content" type="textarea" style="width: 90%;"/>
          <el-input v-else v-model="task_cp.content" type="textarea" style="width: 90%;" disabled/>
        </el-form-item>
        <el-form-item label="备注" label-width="20%">
          <el-input v-model="task_cp.comment" type="textarea" style="width: 90%;"/>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-popover v-model="deleteTaskVisible" placement="top" style="float: right; margin-top: 20px">
          <p>确定要删除这条任务记录吗？</p>
          <div style="text-align: right; margin: 0">
            <el-button slot="reference" size="mini" type="text" @click="deleteTaskVisible=false; test(1)">取消</el-button>
            <el-button slot="reference" type="primary" size="mini" @click="deleteTaskVisible=false;dialogFormVisible=false;deleteTask(task_cp)">确定</el-button>
          </div>
        </el-popover>
        <el-button @click="dialogFormVisible=false;">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible=false; updateTask(task_cp)">确 定</el-button>
        <!--只有自己发布的任务可以删除-->
        <el-button v-if="task_cp.creator.toString()===id" type="danger" style="margin-left: 9px" @click="deleteTaskVisible=true">删 除</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="addTaskVisible" title="添加" width="600px">
      <el-form>
        <el-form-item label="截止时间" label-width="20%">
          <el-date-picker v-model="task_cp.endTime" type="datetime" placeholder="选择日期时间" style="width: 90%;"/>
        </el-form-item>
        <el-form-item label="任务描述" label-width="20%">
          <el-input v-model="task_cp.content" type="textarea" style="width: 90%;"/>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addTaskVisible=false;">取 消</el-button>
        <el-button type="primary" @click="addTaskVisible=false; addTask(task_cp)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAnotherId, getAnotherUsername, getId, getName, getUsername } from '../../utils/auth'
import { getTaskList, updateTask } from '../../api/module/event/task'
import draggable from 'vuedraggable'
import { formatTimeWithoutSecondAndYear, getNowFormatTime } from '../../utils/dateTime'

export default {
  components: {
    draggable // 引入拖拽组件
  },
  data() {
    return {
      drag: false, // 拖拽开启与关闭
      dialogFormVisible: false, // 编辑对话框
      deleteTaskVisible: false, // 删除任务小的弹框
      task_cp: {
        typeId: 0,
        num: 0,
        content: '',
        startTime: '',
        endTime: '',
        status: '',
        creator: 0,
        tag: '',
        comment: '',
        isDeleted: 0
      }, // 为了解决对话框问题，用一个任务副本，每次打开对话框时赋值对应的任务属性进行操作
      addTaskVisible: false, // 添加任务弹窗
      addTaskType: null, // 添加任务类型
      id: getId(),
      user: getUsername(),
      name: getName(),
      anotherId: getAnotherId(),
      anotherUserName: getAnotherUsername(),
      typeId: this.$route.query.typeId,
      totalTaskList: [],
      todoTaskList: [],
      doingTaskList: [],
      doneTaskList: []
    }
  },
  created() {
    this.getTaskList(this.typeId)
  },
  methods: {
    getTaskList(typeId) {
      this.totalTaskList = []
      this.todoTaskList = []
      this.doingTaskList = []
      this.doneTaskList = []
      getTaskList(typeId).then(response => {
        const taskData = response.data
        for (let i = 0; i < taskData.length; ++i) {
          if (taskData[i].status === 'total') this.totalTaskList.push(taskData[i])
          else if (taskData[i].status === 'todo') this.todoTaskList.push(taskData[i])
          else if (taskData[i].status === 'doing') this.doingTaskList.push(taskData[i])
          else if (taskData[i].status === 'done') this.doneTaskList.push(taskData[i])
        }
      })
    },
    // 开始拖拽事件
    onStart(e) {
      this.drag = true
      console.log()
    },
    // 拖拽结束事件
    onEnd() {
      this.drag = false
      // 每一次拖拽事件结束时需要更新任务的类型
      // 遍历三个list，只要有status不匹配的就发送至服务器
      let updateT = null
      for (const i in this.todoTaskList) {
        if (this.todoTaskList[i].status !== 'todo') {
          this.todoTaskList[i].status = 'todo'
          updateT = this.todoTaskList[i]
          break
        }
      }
      if (updateT === null) {
        for (const i in this.doingTaskList) {
          if (this.doingTaskList[i].status !== 'doing') {
            this.doingTaskList[i].status = 'doing'
            updateT = this.doingTaskList[i]
            break
          }
        }
      }
      if (updateT === null) {
        for (const i in this.doneTaskList) {
          if (this.doneTaskList[i].status !== 'done') {
            this.doneTaskList[i].status = 'done'
            updateT = this.doneTaskList[i]
            break
          }
        }
      }
      this.updateTask(updateT)
    },
    // 转化为没有秒的时间格式
    dealTime(time) {
      return formatTimeWithoutSecondAndYear(time)
    },
    /**
     * 逻辑删除任务
     */
    deleteTask(task) {
      task.isDeleted = 1
      this.updateTask(task)
      // 删除需要重新接收一下数据，或者从列表中把数据删掉
      // this.getTaskList()
    },
    /**
     * 更新任务
     */
    updateTask(task) {
      updateTask(task).then(response => {
        console.log('任务状态已更新')
        this.getTaskList(this.typeId)// todo 这里最好不要每次更新都重新获取数据
      }).catch(error => {
        console.log(error + '任务状态更新出错')
      })
    },
    /**
     * 添加任务
     */
    addTask(task) {
      task.status = this.addTaskType
      task.creator = this.id
      task.typeId = this.typeId
      task.startTime = getNowFormatTime()
      task.num = null // num置为null表明是插入操作
      task.isDeleted = 0
      this.updateTask(task)
      // this.getTaskList() // 添加必须得重新获取一次数据，因为要获取该任务的num
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::-webkit-scrollbar {display:none} // 隐藏滚动条
  .board {
    /**
      todo 这里到底是百分比还是固定像素待定
     */
    width: 1400px;
    height: 870px;
    //background: rgba(230,187,217,1);
    //overflow-x: scroll;
  }
  .total_task {
    height: 830px;
    background: #52aeff;
    border-radius: 50px 0px 50px 0px;
    margin:30px;
    box-shadow: 0 0 10px rgba(6, 0, 255, 0.51);
    &:hover {
      background-color: rgba(6,0,255,0.51) !important; // 子模块的hover背景色
    }
    .total_task_item {
      margin: 10px auto;
      box-shadow: 0 0 7px rgba(0, 0, 0, 10);
      width: 95%;
      height: 150px;
      cursor: pointer; // 修改鼠标放在上面的样式
      border-radius: 10px;
      .total_task_item_content {
        margin: 0;
        padding: 10px;
        border-radius: 0 0 10px 10px;
        background: white;
        height: 80px;
        font-size: 18px;
        font-family: 微软雅黑;
        overflow-y: scroll;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
      }
      .total_task_item_msg {
        border-radius: 5px 5px 0px 0px;
        padding-left: 10px;
        overflow-y: scroll;
        background: #970096;
        font-size: 16px;
        font-weight: bold;
        font-family: Arial;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
        color: white;
      }
    }
  }
  .todo_task {
    height: 830px;
    background: #52aeff;
    margin: 30px;
    border-radius: 50px 0px 50px 0px;
    box-shadow: 0 0 10px rgba(244, 233, 0, 0.51);
    &:hover {
      background-color: rgba(244,233,0,0.51)!important; // 子模块的hover背景色
    }
    .todo_task_item {
      margin: 10px auto;
      box-shadow: 0 0 7px rgba(0, 0, 0, 10);
      width: 95%;
      height: 150px;
      cursor: pointer; // 修改鼠标放在上面的样式
      border-radius: 10px;
      .todo_task_item_content {
        margin: 0;
        padding: 10px;
        border-radius: 0 0 10px 10px;
        background: white;
        height: 80px;
        font-size: 18px;
        font-family: 微软雅黑;
        overflow-y: scroll;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
      }
      .todo_task_item_msg {
        border-radius: 5px 5px 0px 0px;
        padding-left: 10px;
        overflow-y: scroll;
        background: #c1a400;
        font-size: 16px;
        font-weight: bold;
        font-family: Arial;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
        color: white;
      }
    }
  }
  .doing_task {
    height: 830px;
    background: #52aeff;
    border-radius: 50px 0px 50px 0px;
    margin:30px;
    box-shadow: 0 0 10px rgba(244,0,5,0.51);
    &:hover {
      background-color: rgba(244,0,5,0.51) !important; // 子模块的hover背景色
    }
    .doing_task_item {
      margin: 10px auto;
      box-shadow: 0 0 7px rgba(0, 0, 0, 10);
      width: 95%;
      height: 150px;
      cursor: pointer; // 修改鼠标放在上面的样式
      border-radius: 10px;
      .doing_task_item_content {
        margin: 0;
        padding: 10px;
        border-radius: 0 0 10px 10px;
        background: white;
        height: 80px;
        font-size: 18px;
        font-family: 微软雅黑;
        overflow-y: scroll;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
      }
      .doing_task_item_msg {
        border-radius: 5px 5px 0px 0px;
        padding-left: 10px;
        overflow-y: scroll;
        background: crimson;
        font-size: 16px;
        font-weight: bold;
        font-family: Arial;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
        color: white;
      }
    }
  }
  .done_task {
    height: 830px;
    background: #52aeff;
    border-radius: 50px 0px 50px 0px;
    margin:30px;
    box-shadow: 0 0 10px rgba(0,255,19,0.51);
    &:hover {
      background-color: rgba(0,255,19,0.51) !important; // 子模块的hover背景色
    }
    .done_task_item {
      margin: 10px auto;
      box-shadow: 0 0 7px rgba(0, 0, 0, 10);
      width: 95%;
      height: 150px;
      cursor: pointer; // 修改鼠标放在上面的样式
      border-radius: 10px;
      .done_task_item_content {
        margin: 0;
        padding: 10px;
        border-radius: 0 0 10px 10px;
        background: white;
        height: 80px;
        font-size: 18px;
        font-family: 微软雅黑;
        overflow-y: scroll;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
      }
      .done_task_item_msg {
        border-radius: 5px 5px 0px 0px;
        padding-left: 10px;
        overflow-y: scroll;
        background: #009004;
        font-size: 16px;
        font-weight: bold;
        font-family: Arial;
        box-shadow: 0 0 2px rgba(0, 0, 0, 1);
        color: white;
      }
    }
  }
  .ghostClass{
    background-color:  blue !important;
  }
  .chosenClass{
    background-color: #f4cedd !important;
    opacity: 1!important;
  }
  .dragClass{
    background-color: blueviolet !important;
    opacity: 1 !important;
    box-shadow:none !important;
    outline:none !important;
    background-image:none !important;
  }
  .dragList {
    height: 730px;
    min-height: 400px;
    //background: pink;
    overflow-y: scroll;
  }
  .task_tittle {
    height: 50px;
    background: black;
    font-size: 30px;
    font-family: "Arial Black";
    text-align: center;
    border-radius: 50px 0 50px 0px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 4);
    color:white;
    text-shadow:
      0px 1px 0px #c0c0c0,
      0px 2px 0px #b0b0b0,
      0px 3px 0px #a0a0a0,
      0px 4px 0px #909090,
      0px 5px 10px rgba(0, 0, 0, 0.6);
    padding-top: 5px;
    margin-bottom: 5px;
  }
  .task_edit {
    color: #52aeff;
    margin-top: 8px;
    font-size: 15px;
    float: right;
  }
  .add_button {
    margin: 10px auto;
    box-shadow: 0 0 7px rgba(0, 0, 0, 10);
    width: 95%;
    height: 40px;
    border-radius: 10px;
    font-size: 20px;
    cursor: pointer;
    text-align: center;
    padding-top: 10px;
    font-weight: bold;
    background: white;
    &:hover {
      background-color: rgba(230,187,217,1) !important; // 子模块的hover背景色
    }
  }
</style>
