// 页面加载时自动调用一次方法，传递空参数
window.onload = function() {
    GetCheckSalaryStd('', '', ''); // 这里空参数可以根据需要调整
};

//隐藏详情盒子
document.getElementById('HiddenDetailButton').addEventListener('click', function() {
    hiddenMask();
    var overlayBox = document.getElementById('DetailBox');
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

const varietyMap = {
    '1': '新增',
    '2': '修改',
    '3': '删除'
};

const checkedMap = {
    '0': '未复核',
    '1': '已通过',
    '2': '已否决'
};



// 发送POST请求并动态更新表格内容
function GetCheckSalaryStd(checkid, sname, checked) {
    // 发送POST请求
    axios.post('/CheckSalaryStdController/getCheckSalaryStd', {
        checkid: checkid,
        sname: sname,
        checked: checked
    })
        .then(function (response) {
            if(response.data.code === 200){
                const data = response.data.data; // 假设返回的数据是一个数组
                const salaryItems = document.getElementById('salaryItems').getElementsByTagName('tbody')[0];

                // 清空现有表格内容
                salaryItems.innerHTML = '';

                // 遍历数据，填充到表格
                data.forEach((item, index) => {
                    // 创建一行表格
                    const row = salaryItems.insertRow();

                    row.insertCell(0).textContent = index + 1;
                    row.insertCell(1).textContent = item.sname;
                    row.insertCell(2).textContent = varietyMap[item.variety];
                    row.insertCell(3).textContent = checkedMap[item.checked];

                    // 为每行绑定点击事件，显示DetailBox并传递行数据
                    row.addEventListener('click', function() {
                        showDetailBox(item); // 将整行数据传递给showDetailBox函数
                    });

                });
            }else{
                console.log('请求数据失败:', error);
            }
        })
        .catch(function (error) {
            console.log('请求数据失败:', error);
        });
}

// 显示DetailBox并填充详细数据
const detailBox = document.getElementById('DetailBox');
const checkidSpan = document.getElementById('checkid');
const sidSpan = document.getElementById('sid');
const snameSpan = document.getElementById('sname');
const totalSpan = document.getElementById('total');
const makerSpan = document.getElementById('maker');
const registerSpan = document.getElementById('register');
const stimeSpan = document.getElementById('stime');
const opinionSpan = document.getElementById('opinion');


function showDetailBox(data) {
    showMask();
    detailBox.style.display = 'block'; // 显示DetailBox

    checkidSpan.textContent = data.checkid;
    sidSpan.textContent = data.sid;
    snameSpan.textContent = data.sname;
    totalSpan.textContent = data.total;
    makerSpan.textContent = data.maker;
    registerSpan.textContent = data.register;
    stimeSpan.textContent = StringToTime(data.stime);
    opinionSpan.textContent = data.opinion;

    updateSalaryList(data.total);
}


document.getElementById('PutSelectButton').addEventListener('click', function (){
    // 获取输入框中的数据
    const checkid = document.getElementById('select-checkid').value;
    const sname = document.getElementById('select-sname').value;
    const checked = document.getElementById('select-checked').value;  // 获取checkbox的状态

    // 调用 GetCheckSalaryStd 函数，并传递参数
    GetCheckSalaryStd(checkid, sname, checked);

    // 清空输入框中的内容
    document.getElementById('select-checkid').value = '';
    document.getElementById('select-sname').value = '';
    document.getElementById('select-checked').value = ''; // 清空复选框
});


const salaryTableBody = document.getElementById('detail-salaryItems').getElementsByTagName('tbody')[0];

// 计算薪酬并更新表格的函数
function updateSalaryList(total) {
    const totalSalary = parseFloat(total); // 获取输入的薪酬金额

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



// 获取按钮元素
const approveBtn = document.getElementById('approve-btn');
const rejectBtn = document.getElementById('reject-btn');
const checkid = document.getElementById('checkid');

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
    axios.post('/CheckSalaryStdController/checkSalaryStd', {
        checkid: checkid,
        isagree: isagree
    })
        .then(response => {
            if(response.data.code === 200){
                hiddenMask();
                var overlayBox = document.getElementById('DetailBox');
                overlayBox.style.display = 'none';
                GetCheckSalaryStd('', '', '');
                console.log('请求成功:', response.data);
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