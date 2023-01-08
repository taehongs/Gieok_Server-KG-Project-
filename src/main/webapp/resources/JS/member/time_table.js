//////////////////////////////////////////////////////////////////////
const selectDay = document.getElementById("day");

let scheduleItemCount;
let scheduleItemList = document.querySelectorAll(".time_box");
//////////////////////////////////////////////////////////////////////

function addHourSelectOption() {
    /**
     * add : select option
     * 시작시간, 소요시간 옵션 생성
     */

    // const maxHour = 24;
    // const boxHeight = 70;

    const startHour = document.getElementById("hour");
    const timeDuration = document.getElementById("hour2");

    for (let i = 0; i <= 24; i++) {
        let createStartHourChild = document.createElement("option");
        let createTimeDurationChild = document.createElement("option");

        createStartHourChild.setAttribute("value", `${i}`);
        createStartHourChild.innerHTML = `${i}`;
        startHour.append(createStartHourChild);

        createTimeDurationChild.setAttribute("value", `${i}`);
        createTimeDurationChild.innerHTML = `${i}`;
        timeDuration.append(createTimeDurationChild);
    }
}

function addSchedule() {
    /**
     * add, delete : schedule
     * 일정 추가, 일정 삭제
     */

    scheduleItemCount = 0;

    const scheduleArea = document.querySelector(".cont_bottom");
    const addScheduleItemButton = document.querySelector(".cont_bottom>input");

    // add : schedule item
    addScheduleItemButton.addEventListener("click", () => {
        let createScheduleItem = document.createElement("div");
        let createScheduleItemDate = document.createElement("span");
        let createScheduleItemArea = document.createElement("div");
        let createScheduleItemDeleteButton = document.createElement("span");

        createScheduleItem.setAttribute("class", `time_box item${scheduleItemCount}`);
        createScheduleItemDate.setAttribute("class", "date");
        createScheduleItemArea.setAttribute("class", "time_gauge");
        createScheduleItemDeleteButton.setAttribute("class", `del del_item${scheduleItemCount}`);

        scheduleArea.append(createScheduleItem);
        createScheduleItem.appendChild(createScheduleItemDate);
        createScheduleItem.appendChild(createScheduleItemArea);
        createScheduleItem.appendChild(createScheduleItemDeleteButton);
        createScheduleItemDate.innerText = `${scheduleItemCount + 1}`;
        createScheduleItemDeleteButton.innerText = "삭제";

        let scheduleItem = document.querySelector(`.item${scheduleItemCount}`);
        let scheduleItemDeleteButton = document.querySelector(`.del_item${scheduleItemCount}`);

        // delete : schedule item
        scheduleItemDeleteButton.addEventListener("click", () => {
            scheduleItem.remove();

            deleteDaySelectOption(scheduleItemCount);
            deleteSelectTimes(scheduleItemCount);
            scheduleItemCount--;

            addScheduleItemButton.style.top = `${addScheduleItemButtonLocation(scheduleItemCount)}px`;

            for (let i = 0; i < scheduleItemList.length; i++) {
                scheduleItemList[i].firstChild.innerText = `${i + 1}`;
            }
            scheduleItemList = document.querySelectorAll(".time_box");
            addScheduleItemClickEvent();
        });

        scheduleItemCount++;
        addDaySelectOption(scheduleItemCount);

        scheduleItemList = document.querySelectorAll(".time_box");
        addScheduleItemClickEvent();
        addScheduleItemButton.style.top = `${addScheduleItemButtonLocation(scheduleItemCount)}px`;
    });
}

function addScheduleItemClickEvent() {
    /**
     * select : day option
     * 스케쥴 공간 선택 시 일차 선택 기능
     */

    console.log(scheduleItemList);

    // select : day option
    if (scheduleItemList.length > 0) {
        for (let i = 0; i < scheduleItemList.length; i++) {
            scheduleItemList[i].addEventListener("click", () => {
                selectDay[i + 1].selected = true;
                dayOption = i + 1;
                console.log(dayOption);
            });
        }
    }
}

function addScheduleItemButtonLocation(count) {
    /**
     * location : add button
     * 일정 추가 버튼 위치 설정
     */

    const addScheduleItemButtonTop = 20;
    const scheduleItemHeight = 100;

    return addScheduleItemButtonTop + count * scheduleItemHeight;
}

function addDaySelectOption(count) {
    /**
     * add : select option
     * 일차 옵션 생성
     */

    const createDayChild = document.createElement("option");

    createDayChild.setAttribute("value", `${count}`);
    createDayChild.innerHTML = `${count}`;
    selectDay.append(createDayChild);
}

function deleteDaySelectOption(count) {
    /**
     * delete : select option
     * 일차 옵션 삭제
     */

    selectDay.remove(count);
}

//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
addHourSelectOption();
addSchedule();
//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////
const colorPaletteList = document.querySelectorAll(".color_palette>input");
const colorHexValueList = document.querySelectorAll(".color_palette>label");
let colorHexValue;

const selectStartHour = document.getElementById("hour");
const selectStartMinute = document.getElementById("minute");
const selectDurationHour = document.getElementById("hour2");
const selectDurationMinute = document.getElementById("minute2");
let dayOption;
let startHourOption;
let startMinuteOption;
let durationHourOption;
let durationMinuteOption;

const commentInput = document.querySelector(".input_cont>input");
let comment;

let timeTableStartPosition;
let timeTableWidth;
//////////////////////////////////////////////////////////////////////

function reset() {
    /**
     * reset
     * 입력 정보 리셋
     */

    colorHexValue = "#CC2F2F";

    dayOption = "none";
    startHourOption = "none";
    startMinuteOption = "none";
    durationHourOption = "none";
    durationMinuteOption = "none";
    comment = "";

    timeTableStartPosition = 0;
    timeTableWidth = 0;

    colorHexValueList[0].checked;

    selectDay.value = "none";
    selectStartHour.value = "none";
    selectStartMinute.value = "none";
    selectDurationHour.value = "none";
    selectDurationMinute.value = "none";
    commentInput.value = "";
}

function daySetting() {
    /**
     * setting : day
     * 일차 설정
     */

    selectDay.addEventListener("change", () => {
        dayOption = selectDay.value;
    });

    return selectDay;
}

function colorSetting() {
    /**
     * setting : color
     * 색상 설정
     */

    for (let i = 0; i < colorPaletteList.length; i++) {
        colorPaletteList[i].addEventListener("click", () => {
            if (colorPaletteList[i].checked) {
                colorHexValue = colorHexValueList[i].innerText;
            }
        });
    }
}

function timeSetting() {
    /**
     * setting : time
     * 시간 설정
     */

    selectDay.addEventListener("change", () => {
        dayOption = selectDay.value;
        timeTableSetting();
    });

    selectStartHour.addEventListener("change", () => {
        startHourOption = parseInt(selectStartHour.value);
        timeTableSetting();
    });

    selectStartMinute.addEventListener("change", () => {
        startMinuteOption = parseFloat(selectStartMinute.value);
        timeTableSetting();
    });

    selectDurationHour.addEventListener("change", () => {
        durationHourOption = parseInt(selectDurationHour.value);
        timeTableSetting();
    });

    selectDurationMinute.addEventListener("change", () => {
        durationMinuteOption = parseFloat(selectDurationMinute.value);
        timeTableSetting();
        test();
    });
}

function commentSetting() {
    /**
     * setting : comment
     * 코멘트 설정
     */

    commentInput.addEventListener("keyup", () => {
        comment = commentInput.value;
    });
}

function timeTableSetting() {
    /**
     * setting : time table
     * 시간표 시작 위치, 길이 설정
     */

    const widthPerHour = 22;

    timeTableStartPosition = (startHourOption + startMinuteOption) * widthPerHour;
    timeTableWidth = (durationHourOption + durationMinuteOption) * widthPerHour;
}

function test() {
    for (let i = startHourOption + startMinuteOption; i < startHourOption + startMinuteOption + durationHourOption + durationMinuteOption; i += 0.5) {
        console.log(i);
    }
}

//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
reset();
daySetting();
colorSetting();
timeSetting();
commentSetting();
//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////
let selectTimes = {
    // day : [0, 0, 0],
};

let addTimeSet = [];

const cursorLine = document.getElementById("cursorLine");
const ruler = document.querySelector(".cont_top>svg");
//////////////////////////////////////////////////////////////////////

function validateCheck(startTime, durationTime) {
    /**
     * check : validate
     * 입력 값 유효성 체크
     */

    let endTime = startTime + durationTime;

    if (dayOption == "none") {
        alert("일자를 선택해주세요");
        return false;
    }

    if (startHourOption == "none" || startMinuteOption == "none") {
        alert("시작 시간을 선택해 주세요");
        return false;
    }

    if (durationHourOption == "none" || durationMinuteOption == "none") {
        alert("소요 시간을 선택해 주세요");
        return false;
    }

    if (durationHourOption == 0) {
        if (durationMinuteOption == 0 || durationMinuteOption == "none") {
            alert("소요 시간은 최소 30분 부터 입력 가능합니다");
            selectDurationMinute.value = 0.5;
            durationMinuteOption = 0.5;
            return false;
        }
    }

    if (endTime > 24) {
        alert("일정은 최대 24시까지 설정 가능합니다");
        return false;
    }

    return true;
}

function addTimeTable() {
    /**
     * add : time table
     * 스케줄에 시간표 추가
     */

    const addTimeTableButton = document.querySelector(".button_box>input:first-of-type");
    const timeTableResetButton = document.querySelector(".button_box>input:last-of-type");
    const scheduleItem = document.querySelectorAll(".time_box");

    addTimeTableButton.addEventListener("click", () => {
        startTime = startHourOption + startMinuteOption;
        durationTime = durationHourOption + durationMinuteOption;
        if (!validateCheck(startTime, durationTime)) {
            return false;
        }

        if (!addSelectTimes()) {
            return false;
        }

        let scheduleItemArea = document.querySelector(`.time_box:nth-of-type(${dayOption})>.time_gauge`);
        let createScheduleItemChild = document.createElement("div");
        createScheduleItemChild.style.backgroundColor = `${colorHexValue}`;
        createScheduleItemChild.style.left = `${timeTableStartPosition}px`;
        createScheduleItemChild.style.width = `${timeTableWidth}px`;
        createScheduleItemChild.innerText = `${comment}`;

        reset();
        scheduleItemArea.append(createScheduleItemChild);
        return true;
    });
}

function duplicateCheck() {
    /**
     * check : duplicate
     * 시간 중복 체크
     */
    startTime = startHourOption + startMinuteOption;
    endTime = startTime + durationHourOption + durationMinuteOption;
    addTimeSet = [];

    if (selectTimes[dayOption] == undefined) {
        selectTimes[dayOption] = [];
    }

    for (let i = startTime; i < endTime; i += 0.5) {
        addTimeSet.push(i);
    }

    duple: for (let i = 0; i < selectTimes[dayOption].length; i++) {
        for (let j = 0; j < addTimeSet.length; j++) {
            if (selectTimes[dayOption][i] == addTimeSet[j]) {
                alert("이미 선택된 시간입니다. 중복되는 시간을 확인해주세요");
                addTimeSet = null;
                break duple;
            }
        }
    }

    return addTimeSet;
}

function addSelectTimes() {
    /**
     * add : select time set
     * 일정별 선택된 시간 추가
     */

    addTimeSet = duplicateCheck();

    if (addTimeSet == null) {
        return false;
    }

    addTimeSet.forEach((item) => {
        selectTimes[dayOption].push(item);
    });

    return true;
}

function deleteSelectTimes(deleteDay) {
    /**
     * delete : select time set
     * 선택된 일정 시간 삭제
     */

    delete selectTimes[deleteDay];

    console.log(selectTimes + " 삭제");
    for (let i = deleteDay; i < selectTimes.length; i++) {
        if ((i = selectTimes.length - 1)) {
            delete selectTimes[selectTimes.length - 1];
            return;
        }
        selectTimes[i] = selectTimes[i + 1];
    }
    console.log(selectTimes + " 삭제");

    // 키 값을 하나씩 밀어줘야함
}

function addMouseLine() {
    /**
     * add : mouse line
     * 시간 눈금자 표시선
     */

    ruler.addEventListener("mouseenter", () => {
        cursorLine.style.display = "block";
    });

    ruler.addEventListener("mouseleave", () => {
        cursorLine.style.display = "none";
    });

    ruler.onmousemove = (event) => {
        cursorLine.style.left = event.pageX - 413 + "px";
    };
}

//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
addTimeTable();
addMouseLine();
//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
