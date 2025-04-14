                                                                                   window.onload = function (){
    getIssueSalary('', '', '', 1);
    getIssueCheckSalary('', '', '', 0);
}


//打开查询盒子
document.getElementById('ShowSelectButton').addEventListener('click', function() {
    showMask1();
    var overlayBox = document.getElementById('SelectBox');
    overlayBox.style.display = 'block';
});

//隐藏查询盒子
document.getElementById('HiddenSelectButton').addEventListener('click', function() {
    hiddenMask1();
    var overlayBox = document.getElementById('SelectBox');
    overlayBox.style.display = 'none';
});

//打开新增盒子
document.getElementById('ShowAddButton').addEventListener('click', function() {
    showMask1();
    var overlayBox = document.getElementById('AddBox');
    overlayBox.style.display = 'block';
});

//隐藏新增盒子
document.getElementById('HiddenAddButton').addEventListener('click', function() {
    hiddenMask1();
    var overlayBox = document.getElementById('AddBox');
    overlayBox.style.display = 'none';
});

//打开复核盒子
document.getElementById('ShowCheckButton').addEventListener('click', function() {
    showMask1();
    var overlayBox = document.getElementById('CheckBox');
    overlayBox.style.display = 'block';
});

//隐藏复核盒子
document.getElementById('HiddenCheckButton').addEventListener('click', function() {
    hiddenMask1();
    var overlayBox = document.getElementById('CheckBox');
    overlayBox.style.display = 'none';
});

//使遮罩层出现
function showMask1(){
    var Mask = document.getElementById('Mask1');
    Mask.style.display = 'block';
}

//使遮罩层隐藏
function hiddenMask1(){
    var Mask = document.getElementById('Mask1');
    Mask.style.display = 'none';
}

document.getElementById('top-button').addEventListener('click', function (){
    var overlayBox = document.getElementById('SelectBox-DetailBox');
    overlayBox.style.display = 'none';
})

const checkMap = {
    '0': '未登记',
    '1': '已登记'
}


const select_dpid = document.getElementById('select-dpid');
const select_department = document.getElementById('select-department');
const select_register = document.getElementById('select-register');


document.getElementById('PutSelectButton').addEventListener('click', function (){

    const select_dpid_value = select_dpid.value;
    const select_department_value = select_department.value;
    const select_register_value = select_register.value;

    getIssueSalary(select_dpid_value, select_department_value, select_register_value, 1)
})

function getIssueSalary(dpid, department, register, checked){

    axios.post('/IssueSalaryController/getDepartmentPayroll',
        {
            dpid: dpid,
            department: department,
            register: register,
            checked: checked
        })
        .then(function(response) {
            if (response.data.code === 200) {
                const salaryList = response.data.data;
                    addIssueSalaryToSelectBoxInCheck(salaryList);
            } else {
                alert("提交失败，请稍后重试！");
            }
        })
        .catch(function(error) {
            console.error("提交数据时出错：", error);
            alert("提交失败，请稍后重试！");
        });
}

const salaryTableBody = document.getElementById('salaryItems').getElementsByTagName('tbody')[0];

function addIssueSalaryToSelectBoxInCheck(salaryList = []){
    salaryTableBody.innerHTML = '';
    salaryList.forEach((item) => {
        console.log(item);
        const row = salaryTableBody.insertRow();
        row.insertCell(0).textContent = item.dpid;
        row.insertCell(1).textContent = item.onedepartment;
        row.insertCell(2).textContent = item.twodepartment;
        row.insertCell(3).textContent = item.threedepartment;
        row.insertCell(4).textContent = item.number;
        row.insertCell(5).textContent = item.departmentsalary;
        row.insertCell(6).textContent = checkMap[item.checked];

        row.addEventListener('click', function() {
            addIssueSalaryToDeatilBoxInCheck(item);
        });
    });

}

const detail_select_dpid = document.getElementById('detail-select-dpid');
const detail_select_department = document.getElementById('detail-select-department');
const detail_select_number = document.getElementById('detail-select-number');
const detail_select_departmentsalary = document.getElementById('detail-select-departmentsalary');
const detail_select_register = document.getElementById('detail-select-register');
const detail_select_dtime = document.getElementById('detail-select-dtime');
const detail_salaryItems = document.getElementById('detail-salaryList').getElementsByTagName('tbody')[0];

function addIssueSalaryToDeatilBoxInCheck(item){
    const overlayBox = document.getElementById('SelectBox-DetailBox');
    overlayBox.style.display = 'block';
    detail_select_dpid.textContent = item.dpid;
    detail_select_department.textContent = item.onedepartment + '/' + item.twodepartment + '/' + item.threedepartment;
    detail_select_number.textContent = item.number;
    detail_select_departmentsalary.textContent = item.departmentsalary;
    detail_select_register.textContent = item.register;
    detail_select_dtime.textContent = StringToTime(item.dtime);



    // 计算各项薪酬项目
    // const basicSalary = totalSalary;
    // const pension = basicSalary * 0.08; // 养老保险 8%
    // const medical = basicSalary * 0.02 + 3; // 医疗保险 2% + 3元
    // const unemployment = basicSalary * 0.005; // 失业保险 0.5%
    // const housingFund = basicSalary * 0.08; // 住房公积金 8%

    getEmployeeByDpidAndFillToDetail(item.dpid, detail_salaryItems);
}

function getEmployeeByDpidAndFillToDetail(dpid, salaryItems){
    axios.get("/IssueSalaryController/getIssueSalaryInEmployeeByDpid?dpid=" + dpid)
        .then(function(response) {
            if(response.data.code === 200){
                salaryItems.innerHTML = '';
                const salaryList = response.data.data;
                salaryList.forEach(item =>{
                    const basicSalary = item.total;
                    const row = salaryItems.insertRow();
                    row.insertCell(0).textContent = item.epid;
                    row.insertCell(1).textContent = item.ename;
                    row.insertCell(2).textContent = basicSalary;
                    row.insertCell(3).textContent = basicSalary * 0.08;
                    row.insertCell(4).textContent = basicSalary * 0.02 + 3;
                    row.insertCell(5).textContent = basicSalary * 0.005;
                    row.insertCell(6).textContent = basicSalary * 0.08;
                })
            }else{
                console.log("员工信息获取失败");
            }
        })
        .catch(function (error){

        });
}



document.getElementById('PutAddButton').addEventListener('click', function (){
    const add_id = Number(document.getElementById('add-id').value);
    addIssueSalary(add_id);
})


function addIssueSalary(id){
    axios.get('/IssueSalaryController/addIssueSalary?id=' + id)
        .then(function(response) {
            if (response.data.code === 200) {
                const dpid = response.data.data;
                if(dpid !== null || dpid !== ''){
                    addIssueSalaryToBoxInAdd(dpid);
                }
            } else {
                alert("提交失败，请稍后重试！");
            }
        })
        .catch(function(error) {
            console.error("提交数据时出错：", error);
            alert("提交失败，请稍后重试！");
        });
}

const add_salaryItems = document.getElementById('add-salaryItems').getElementsByTagName('tbody')[0];

function addIssueSalaryToBoxInAdd(dpid){
    axios.post('/IssueSalaryController/getDepartmentPayroll',
        {
            dpid: dpid
        })
        .then(function(response) {
            if (response.data.code === 200) {
                const salaryList = response.data.data;
                salaryList.forEach(item => {
                    add_salaryItems.innerHTML = '';
                    const row = add_salaryItems.insertRow();
                    row.insertCell(0).textContent = item.dpid;
                    row.insertCell(1).textContent = item.onedepartment;
                    row.insertCell(2).textContent = item.twodepartment;
                    row.insertCell(3).textContent = item.threedepartment;
                    row.insertCell(4).textContent = item.number;
                    row.insertCell(5).textContent = item.departmentsalary;
                })

            } else {
                alert("提交失败，请稍后重试！");
            }
        })
        .catch(function(error) {
            console.error("提交数据时出错：", error);
            alert("提交失败，请稍后重试！");
        });
}

const check_dpid = document.getElementById('check-dpid');
const check_department = document.getElementById('check-department');
const check_register = document.getElementById('check-register');
document.getElementById('PutCheckButton').addEventListener('click', function (){

    const check_dpid_value = check_dpid.value;
    const check_department_value = check_department.value;
    const check_register_value = check_register.value;

    getIssueCheckSalary(check_dpid_value, check_department_value, check_register_value, 0)
})

function getIssueCheckSalary(dpid, department, register, checked){
    axios.post('/IssueSalaryController/getDepartmentPayroll',
        {
            dpid: dpid,
            department: department,
            register: register,
            checked: checked
        })
        .then(function(response) {
            if (response.data.code === 200) {
                const salaryList = response.data.data;
                addIssueSalaryToCheckBoxInNoCheck(salaryList);
            } else {
                alert("提交失败，请稍后重试！");
            }
        })
        .catch(function(error) {
            console.error("提交数据时出错：", error);
            alert("提交失败，请稍后重试！");
        });
}

const checkTableBody = document.getElementById('checkItems').getElementsByTagName('tbody')[0];

function addIssueSalaryToCheckBoxInNoCheck(salaryList){
    checkTableBody.innerHTML = '';
    salaryList.forEach((item) => {
        console.log(item);
        const row = checkTableBody.insertRow();
        row.insertCell(0).textContent = item.dpid;
        row.insertCell(1).textContent = item.onedepartment;
        row.insertCell(2).textContent = item.twodepartment;
        row.insertCell(3).textContent = item.threedepartment;
        row.insertCell(4).textContent = item.number;
        row.insertCell(5).textContent = item.departmentsalary;
        row.insertCell(6).textContent = checkMap[item.checked];

        row.addEventListener('click', function() {
            addIssueSalaryToDeatilBoxInNoCheck(item);
        });
    });
}

const detail_check_dpid = document.getElementById('detail-check-dpid');
const detail_check_department = document.getElementById('detail-check-department');
const detail_check_number = document.getElementById('detail-check-number');
const detail_check_departmentsalary = document.getElementById('detail-check-departmentsalary');
const detail_check_register = document.getElementById('detail-check-register');
const detail_check_dtime = document.getElementById('detail-check-dtime');
const detail_checkItems = document.getElementById('detail-checkList').getElementsByTagName('tbody')[0];
function addIssueSalaryToDeatilBoxInNoCheck(item){

    const overlayBox = document.getElementById('CheckBox-DetailBox');
    overlayBox.style.display = 'block';
    detail_check_dpid.textContent = item.dpid;
    detail_check_department.textContent = item.onedepartment + '/' + item.twodepartment + '/' + item.threedepartment;
    detail_check_number.textContent = item.number;
    detail_check_departmentsalary.textContent = item.departmentsalary;
    detail_check_register.textContent = item.register;
    detail_check_dtime.textContent = StringToTime(item.dtime);

    getEmployeeByDpidAndFillToDetail(item.dpid, detail_checkItems);
}
// detail_checkItems.innerHTML = '';

//打开查询盒子
document.getElementById('check-cancel-button').addEventListener('click', function() {
    const overlayBox = document.getElementById('CheckBox-DetailBox');
    overlayBox.style.display = 'none';
});


// 获取按钮元素
const approveBtn = document.getElementById('check-pass-button');
const rejectBtn = document.getElementById('check-unpass-button');
const checkid = document.getElementById('detail-check-dpid');

// 给按钮绑定点击事件
approveBtn.addEventListener('click', function() {
    const checkidValue = checkid.textContent;
    sendApprovalRequest(checkidValue, 1);  // isagree为1表示通过
});

rejectBtn.addEventListener('click', function() {
    const checkidValue = checkid.textContent;
    sendApprovalRequest(checkidValue, 0);  // isagree为0表示否决
});
// 发送请求的函数
function sendApprovalRequest(checkid, isagree) {

    // 发送POST请求
    axios.post('/IssueSalaryController/checkIssueSalary', {
        checkid: checkid,
        isagree: isagree
    })
        .then(response => {
            if(response.data.code === 200){
                var overlayBox = document.getElementById('CheckBox-DetailBox');
                overlayBox.style.display = 'none';
                getIssueCheckSalary('', '', '', 0);
                console.log('复核成功:');
            }else{
                console.log('复核失败:');
            }
            // 可以在这里处理响应数据
        })
        .catch(error => {
            console.error('复核失败:', error);
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
