// 页面加载完成后自动调用此函数
window.onload = function() {
    getSalaryStd();
};

//--------------样式函数---------------

//打开查询盒子
document.getElementById('ShowSelectButton').addEventListener('click', function() {
    showMask();
    var overlayBox = document.getElementById('SelectBox');
    overlayBox.style.display = 'block';
});

//隐藏查询盒子
document.getElementById('HiddenSelectButton').addEventListener('click', function() {
    hiddenMask();
    var overlayBox = document.getElementById('SelectBox');
    overlayBox.style.display = 'none';
});

//打开新增盒子
document.getElementById('ShowAddButton').addEventListener('click', function() {
    showMask();
    var overlayBox = document.getElementById('AddBox');
    overlayBox.style.display = 'block';
});

//隐藏新增盒子
document.getElementById('HiddenAddButton').addEventListener('click', function() {
    hiddenMask();
    var overlayBox = document.getElementById('AddBox');
    overlayBox.style.display = 'none';
});

//隐藏修改盒子
document.getElementById('HiddenUpdateButton').addEventListener('click', function() {
    hiddenMask();
    var overlayBox = document.getElementById('UpdateBox');
    overlayBox.style.display = 'none';
});

//隐藏删除盒子
document.getElementById('HiddenDeleteButton').addEventListener('click', function() {
    hiddenMask();
    var overlayBox = document.getElementById('DeleteBox');
    overlayBox.style.display = 'none';
});

//使遮罩层出现
function showMask(){
    var Mask = document.getElementById('Mask');
    Mask.style.display = 'block';
}

//使遮罩层隐藏
function hiddenMask(){
    var Mask = document.getElementById('Mask');
    Mask.style.display = 'none';
}

// 更新当前日期函数
function updateDate() {
    // 获取当前日期
    const now = new Date();
    // 格式化日期为 'YYYY-MM-DD' 形式
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');  // 月份从0开始，所以需要加1
    const day = String(now.getDate()).padStart(2, '0');
    const dateString = `${year}-${month}-${day}`;

    // 将日期显示到 input 框中
    document.getElementById('add-stime').value = dateString;
}
// 每天更新一次日期（虽然它会不变，仍然调用以确保更新）
setInterval(updateDate, 1000 * 24 * 60 * 60);  // 86400000毫秒等于 24小时
// 初始化显示一次
updateDate();


// 获取输入框和表格
const salaryInput = document.getElementById('add-total');
const salaryTableBody = document.getElementById('add-salaryItems').getElementsByTagName('tbody')[0];
// 为输入框添加事件监听器，监听输入的变化
salaryInput.addEventListener('input', updateSalaryList);
// 计算薪酬并更新表格的函数
function updateSalaryList() {
    const totalSalary = parseFloat(salaryInput.value); // 获取输入的薪酬金额

    // 如果输入的金额无效（如为空或负数），则清空表格
    if (isNaN(totalSalary) || totalSalary <= 0) {
        salaryTableBody.innerHTML = '';
        return;
    }

    // 计算各项薪酬项目
    const basicSalary = totalSalary;
    const pension = basicSalary * 0.08; // 养老保险 8%
    const medical = basicSalary * 0.02 + 3; // 医疗保险 2% + 3元
    const unemployment = basicSalary * 0.005; // 失业保险 0.5%
    const housingFund = basicSalary * 0.08; // 住房公积金 8%

    // 定义薪酬项目列表
    const salaryItems = [
        { name: '基本工资', amount: basicSalary },
        { name: '养老保险', amount: pension },
        { name: '医疗保险', amount: medical },
        { name: '失业保险', amount: unemployment },
        { name: '住房公积金', amount: housingFund }
    ];

    // 清空表格内容
    salaryTableBody.innerHTML = '';

    // 动态插入每个薪酬项目到表格
    salaryItems.forEach((item, index) => {
        const row = salaryTableBody.insertRow();
        row.insertCell(0).textContent = index + 1;
        row.insertCell(1).textContent = item.name;
        row.insertCell(2).textContent = item.amount.toFixed(2);
    });
}

//--------------功能函数---------------
document.getElementById('PutSelectButton').addEventListener('click', function() {
    getSalaryStd();
});

function getSalaryStd(){
    // 获取表单和按钮
    const salaryForm = document.getElementById('salaryForm');

    // 获取表单数据
    const sid = document.getElementById('sid').value;
    const key = document.getElementById('key').value;
    const start_time = document.getElementById('start_time').value;
    const end_time = document.getElementById('end_time').value;

    console.log(start_time);
    console.log(start_time.split(' ')[0]);

    // 使用 axios 发送 POST 请求
    axios.post('/SalaryStdController/getSalaryStd',
        {
            sid: sid,
            key: key,
            start_time: start_time,
            end_time: end_time
        })
        .then(function(response) {
            if (response.data.code === 200) {
                // 获取响应数据
                const salaryList = response.data.data;
                // 清空表单
                salaryForm.reset();
                // 调用函数将响应数据添加到表格
                addSalaryToTable(salaryList);
            } else {
                alert("提交失败，请稍后重试！");
            }
        })
        .catch(function(error) {
            console.error("提交数据时出错：", error);
            alert("提交失败，请稍后重试！");
        });
}


// 将返回的数据添加到表格
function addSalaryToTable(salaryList = []) {
    const salaryItems = document.getElementById('salaryItems').getElementsByTagName('tbody')[0];
    salaryItems.innerHTML = ''; // 清空现有列表


    salaryList.forEach(item => {
        const row = salaryItems.insertRow();

        // 填充数据
        row.insertCell(0).textContent = item.sid;
        row.insertCell(1).textContent = item.sname;
        row.insertCell(2).textContent = item.total;
        row.insertCell(3).textContent = item.maker;
        row.insertCell(4).textContent = item.register;
        row.insertCell(5).textContent = StringToTime(item.stime);
        row.insertCell(6).textContent = item.checker;

        // 新增操作按钮列
        const actionCell = row.insertCell(7);
        actionCell.classList.add('action-cell');

        const editButton = document.createElement('button');
        editButton.textContent = '更改';
        editButton.classList.add('list-btn-modify');
        editButton.addEventListener('click', function (){
            putUpdateDataToTable(item);
        });
        actionCell.appendChild(editButton);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = '删除';
        deleteButton.classList.add('list-btn-delete');
        deleteButton.addEventListener('click', function (){
            putDeleteDataToTable(item);
        })
        actionCell.appendChild(deleteButton);
    });
}


// 为按钮添加点击事件监听器
document.getElementById('ShowAddButton').addEventListener('click', function() {
    // 点击按钮时触发获取数据并填充
    getIdAndRegister();
});
// 定义获取数据并填充输入框的函数
function getIdAndRegister() {
    // 使用 Axios 发起 GET 请求
    axios.get('/SalaryStdController/getIdAndRegister')  // 替换为你的实际 API 地址
        .then(function (response) {
            if(response.data.code === 200){
                const data = response.data.data;
                // 将 sid 和 register 填充到对应的输入框
                document.getElementById('add-sid').value = data.sid;
                document.getElementById('add-register').value = data.register;
            }else{
                console.error('请求数据失败:');
            }
        })
        .catch(function (error) {
            console.error('请求数据失败:', error);
        });
}

// 为提交按钮添加点击事件监听
document.getElementById('PutAddButton').addEventListener('click', function() {
    submitAddForm();
});

// 定义提交表单并发送 POST 请求的函数
function submitAddForm() {
    // 获取表单中的数据
        var sname = document.getElementById('add-sname').value;
        var total = document.getElementById('add-total').value;
        var maker = document.getElementById('add-maker').value;
        var opinion = document.getElementById('add-opinion').value;

    // 使用 Axios 发送 POST 请求
    axios.post('/SalaryStdController/addSalaryStd',
        {
            sname: sname,
            total: total,
            maker: maker,
            opinion: opinion
        })
        .then(function (response) {
            if(response.data.code === 200){
                alert('提交成功');
                // 清空所有表单输入框的值
                document.getElementById('add-sid').value = '';
                document.getElementById('add-sname').value = '';
                document.getElementById('add-total').value = '';
                document.getElementById('add-maker').value = '';
                document.getElementById('add-register').value = '';
                document.getElementById('add-opinion').value = '';

                var overlayBox = document.getElementById('AddBox');
                overlayBox.style.display = 'none';
                hiddenMask();
            }
        })
        .catch(function (error) {
            console.error('提交失败:', error);
            alert('提交失败，请重试');
        });
}

// 定义获取数据并填充输入框的函数
function getRegister(input_register) {
    // 使用 Axios 发起 GET 请求
    axios.get('/SalaryStdController/getRegister')  // 替换为你的实际 API 地址
        .then(function (response) {
            if(response.data.code === 200){
                const data = response.data.data;
                input_register.value = data;
            }else{
                console.error('请求数据失败:');
            }
        })
        .catch(function (error) {
            console.error('请求数据失败:', error);
        });
}

function putUpdateDataToTable(data = []){
    var overlayBox = document.getElementById('UpdateBox');
    overlayBox.style.display = 'block';

    document.getElementById('update-sid').value = data.sid;
    document.getElementById('update-sname').value = data.sname;
    document.getElementById('update-total').value = data.total;
    document.getElementById('update-maker').value = data.maker;
    document.getElementById('update-opinion').value = '';

    // 获取当前日期
    const now = new Date();
    // 格式化日期为 'YYYY-MM-DD' 形式
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');  // 月份从0开始，所以需要加1
    const day = String(now.getDate()).padStart(2, '0');
    const dateString = `${year}-${month}-${day}`;
    // 将日期显示到 input 框中
    document.getElementById('update-stime').value = dateString;
    const input_register = document.getElementById('update-register');
    getRegister(input_register);
    updateSalaryListInUpdate();
}

const updateSalaryInput = document.getElementById('update-total');
const updateSalaryTableBody = document.getElementById('update-salaryItems').getElementsByTagName('tbody')[0];
// 为输入框添加事件监听器，监听输入的变化
updateSalaryInput.addEventListener('input', updateSalaryListInUpdate);
// 计算薪酬并更新表格的函数
function updateSalaryListInUpdate() {
    const totalSalary = parseFloat(updateSalaryInput.value); // 获取输入的薪酬金额

    // 如果输入的金额无效（如为空或负数），则清空表格
    if (isNaN(totalSalary) || totalSalary <= 0) {
        salaryTableBody.innerHTML = '';
        return;
    }

    // 计算各项薪酬项目
    const basicSalary = totalSalary;
    const pension = basicSalary * 0.08; // 养老保险 8%
    const medical = basicSalary * 0.02 + 3; // 医疗保险 2% + 3元
    const unemployment = basicSalary * 0.005; // 失业保险 0.5%
    const housingFund = basicSalary * 0.08; // 住房公积金 8%

    // 定义薪酬项目列表
    const salaryItems = [
        { name: '基本工资', amount: basicSalary },
        { name: '养老保险', amount: pension },
        { name: '医疗保险', amount: medical },
        { name: '失业保险', amount: unemployment },
        { name: '住房公积金', amount: housingFund }
    ];

    // 清空表格内容
    updateSalaryTableBody.innerHTML = '';

    // 动态插入每个薪酬项目到表格
    salaryItems.forEach((item, index) => {
        const row = updateSalaryTableBody.insertRow();
        row.insertCell(0).textContent = index + 1;
        row.insertCell(1).textContent = item.name;
        row.insertCell(2).textContent = item.amount.toFixed(2);
    });
}





function putDeleteDataToTable(data = []){
    var overlayBox = document.getElementById('DeleteBox');
    overlayBox.style.display = 'block';

    document.getElementById('delete-sid').value = data.sid;
    document.getElementById('delete-sname').value = data.sname;
    document.getElementById('delete-total').value = data.total;
    document.getElementById('delete-maker').value = data.maker;
    document.getElementById('delete-opinion').value = '';

    // 获取当前日期
    const now = new Date();
    // 格式化日期为 'YYYY-MM-DD' 形式
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');  // 月份从0开始，所以需要加1
    const day = String(now.getDate()).padStart(2, '0');
    const dateString = `${year}-${month}-${day}`;
    // 将日期显示到 input 框中
    document.getElementById('delete-stime').value = dateString;
    const input_register = document.getElementById('delete-register');
    getRegister(input_register);
    deleteSalaryListInUpdate();
}


const deleteSalaryInput = document.getElementById('delete-total');
const deleteSalaryTableBody = document.getElementById('delete-salaryItems').getElementsByTagName('tbody')[0];
// 计算薪酬并更新表格的函数
function deleteSalaryListInUpdate() {
    const totalSalary = parseFloat(deleteSalaryInput.value); // 获取输入的薪酬金额

    // 如果输入的金额无效（如为空或负数），则清空表格
    if (isNaN(totalSalary) || totalSalary <= 0) {
        deleteSalaryTableBody.innerHTML = '';
        return;
    }

    // 计算各项薪酬项目
    const basicSalary = totalSalary;
    const pension = basicSalary * 0.08; // 养老保险 8%
    const medical = basicSalary * 0.02 + 3; // 医疗保险 2% + 3元
    const unemployment = basicSalary * 0.005; // 失业保险 0.5%
    const housingFund = basicSalary * 0.08; // 住房公积金 8%

    // 定义薪酬项目列表
    const salaryItems = [
        { name: '基本工资', amount: basicSalary },
        { name: '养老保险', amount: pension },
        { name: '医疗保险', amount: medical },
        { name: '失业保险', amount: unemployment },
        { name: '住房公积金', amount: housingFund }
    ];

    // 清空表格内容
    deleteSalaryTableBody.innerHTML = '';

    // 动态插入每个薪酬项目到表格
    salaryItems.forEach((item, index) => {
        const row = deleteSalaryTableBody.insertRow();
        row.insertCell(0).textContent = index + 1;
        row.insertCell(1).textContent = item.name;
        row.insertCell(2).textContent = item.amount.toFixed(2);
    });
}






document.getElementById('PutUpdateButton').addEventListener('click', function() {
    submitUpdateForm();
});

// 定义提交表单并发送 POST 请求的函数
function submitUpdateForm() {
    // 获取表单中的数据
    var sid = document.getElementById('update-sid').value;
    var sname = document.getElementById('update-sname').value;
    var total = document.getElementById('update-total').value;
    var maker = document.getElementById('update-maker').value;
    var opinion = document.getElementById('update-opinion').value;

    // 使用 Axios 发送 POST 请求
    axios.post('/SalaryStdController/modifySalaryStd',
        {
            sid: sid,
            sname: sname,
            total: total,
            maker: maker,
            opinion: opinion
        })
        .then(function (response) {
            if(response.data.code === 200){
                alert('提交成功');
                // 清空所有表单输入框的值
                var overlayBox = document.getElementById('UpdateBox');
                overlayBox.style.display = 'none';
                getSalaryStd();
            }
        })
        .catch(function (error) {
            console.error('提交失败:', error);
            alert('提交失败，请重试');
        });
}




document.getElementById('PutDeleteButton').addEventListener('click', function() {
    submitDeleteForm();
});

// 定义提交表单并发送 POST 请求的函数
function submitDeleteForm() {
    // 获取表单中的数据
    var sid = document.getElementById('delete-sid').value;
    var opinion = document.getElementById('delete-opinion').value;

    // 使用 Axios 发送 POST 请求
    axios.post('/SalaryStdController/removeSalaryStd',
        {
            sid: sid,
            opinion: opinion
        })
        .then(function (response) {
            if(response.data.code === 200){
                alert('提交成功');
                // 清空所有表单输入框的值
                var overlayBox = document.getElementById('DeleteBox');
                overlayBox.style.display = 'none';
                getSalaryStd();
            }
        })
        .catch(function (error) {
            console.error('提交失败:', error);
            alert('提交失败，请重试');
        });
}



function StringToTime(dateString){
    if(dateString === null){
        return null;
    }
    const date = new Date(dateString);

// 获取年月日部分
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');  // 月份从0开始，需要加1，并格式化为两位数
    const day = String(date.getDate()).padStart(2, '0');  // 格式化为两位数

    return `${year}-${month}-${day}`;
}

