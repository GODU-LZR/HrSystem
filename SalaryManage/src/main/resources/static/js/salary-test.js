// API URL (假设后端接口)
const API_URL = 'https://your-api.com/salary';  // 替换为实际接口URL

// 页面加载完成后自动调用此函数
window.onload = function() {
    // loadSalaryList();  // 页面加载时自动加载数据（所有查询条件为空）
    updateSalaryList();
};

// 新增按钮功能：打开弹窗
document.getElementById('addButton').addEventListener('click', function() {
    // 通过修改 display 样式来显示弹框
    document.getElementById('modal').style.display = 'flex';
});

// 点击取消按钮时，关闭弹框
document.getElementById('cancelButton').addEventListener('click', function() {
    // 通过修改 display 样式来隐藏弹框
    document.getElementById('modal').style.display = 'none';
});

// 提交查询表单，进行查询
document.getElementById('salaryForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const salaryName = document.getElementById('salaryName').value;
    const registrant = document.getElementById('registrant').value;

    // 调用查询函数，传递表单数据
    loadSalaryList(salaryName, registrant);
});

// 查询薪酬标准函数
function loadSalaryList(salaryName = '', registrant = '') {
    const queryParams = {
        salaryName: salaryName,
        registrant: registrant
    };

    // 使用axios发送GET请求获取薪酬标准列表
    axios.get(API_URL, { params: queryParams })
        .then(response => {
            // 填充查询结果到列表
            updateSalaryList(response.data);
        })
        .catch(error => {
            console.error('查询失败:', error);
            alert('查询失败，请重试');
        });
}

// 提交弹窗中的表单，新增薪酬标准
document.getElementById('modalForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const salaryName = document.getElementById('modalSalaryName').value;
    const salaryAmount = document.getElementById('modalSalaryAmount').value;
    const registrant = document.getElementById('modalRegistrant').value;
    const creator = document.getElementById('modalCreator').value;

    const salaryData = {
        salaryName: salaryName,
        salaryAmount: salaryAmount,
        registrant: registrant,
        creator: creator
    };

    // 使用axios发送POST请求新增薪酬标准
    axios.post(API_URL, salaryData)
        .then(response => {
            alert('薪酬标准添加成功');
            loadSalaryList();  // 刷新列表
            // 隐藏弹框
            document.getElementById('modal').style.display = 'none';
            document.getElementById('modalForm').reset();  // 清空表单
        })
        .catch(error => {
            console.error('添加薪酬标准失败:', error);
            alert('添加失败，请重试');
        });
});


// 更新薪酬标准列表
function updateSalaryList(salaryList = []) {
    const salaryItems = document.getElementById('salaryItems').getElementsByTagName('tbody')[0];
    salaryItems.innerHTML = ''; // 清空现有列表

    // 添加伪数据行
    salaryList.push({
        salaryName: '伪数据薪酬标准',
        salaryAmount: 5000,
        registrant: '张三',
        creator: '李四'
    });

    salaryList.forEach(item => {
        const row = salaryItems.insertRow();

        // 填充数据
        const salaryNameCell = row.insertCell(0);
        salaryNameCell.classList.add('salary-name');
        salaryNameCell.textContent = item.salaryName;

        const salaryAmountCell = row.insertCell(1);
        salaryAmountCell.classList.add('salary-amount');
        salaryAmountCell.textContent = item.salaryAmount + '元';

        const registrantCell = row.insertCell(2);
        registrantCell.classList.add('registrant');
        registrantCell.textContent = item.registrant;

        const creatorCell = row.insertCell(3);
        creatorCell.classList.add('creator');
        creatorCell.textContent = item.creator;

        // 新增操作按钮列
        const actionCell = row.insertCell(4);
        actionCell.classList.add('action-cell');

        const editButton = document.createElement('button');
        editButton.textContent = '更改';
        editButton.classList.add('btn-action');
        actionCell.appendChild(editButton);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = '删除';
        deleteButton.classList.add('btn-action');
        actionCell.appendChild(deleteButton);
    });
}


// row.querySelector('.salary-name').textContent = item.salaryName;
// row.querySelector('.salary-amount').textContent = item.salaryAmount + '元';
// row.querySelector('.registrant').textContent = item.registrant;
// row.querySelector('.creator').textContent = item.creator;

// row.querySelector('.salary-name').textContent = item.salaryName;
// row.querySelector('.salary-amount').textContent = item.salaryAmount + '元';
// row.querySelector('.registrant').textContent = item.registrant;
// row.querySelector('.creator').textContent = item.creator;