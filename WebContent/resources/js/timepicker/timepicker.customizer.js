function tpStartOnHourShowCallback(hour) {
	if (!PrimeFaces.widgets['endTimeWidget']) {
		return false;
	}

	var tpEndHour = parseInt(PF('endTimeWidget').getHours());
	if (tpEndHour == -1) {
		return true;
	}

	// Check if proposed hour is prior or equal to selected end time hour
	if (parseInt(hour) <= tpEndHour) {
		return true;
	}

	// if hour did not match, it can not be selected
	return false;
}

function tpStartOnMinuteShowCallback(hour, minute) {
	if (!PrimeFaces.widgets['endTimeWidget']) {
		return false;
	}

	var tpEndHour = parseInt(PF('endTimeWidget').getHours());
	var tpEndMinute = parseInt(PF('endTimeWidget').getMinutes());
	if (tpEndHour == -1) {
		return true;
	}

	// Check if proposed hour is prior to selected end time hour
	if (parseInt(hour) < tpEndHour) {
		return true;
	}

	// Check if proposed hour is equal to selected end time hour and minutes is
	// prior
	if ((parseInt(hour) == tpEndHour) && (parseInt(minute) < tpEndMinute)) {
		return true;
	}

	// if minute did not match, it can not be selected
	return false;
}

function tpEndOnHourShowCallback(hour) {
	if (!PrimeFaces.widgets['startTimeWidget']) {
		return false;
	}

	var tpStartHour = parseInt(PF('startTimeWidget').getHours());

	// Check if proposed hour is after or equal to selected start time hour
	if (parseInt(hour) >= tpStartHour) {
		return true;
	}

	// if hour did not match, it can not be selected
	return false;
}

function tpEndOnMinuteShowCallback(hour, minute) {
	if (!PrimeFaces.widgets['startTimeWidget']) {
		return false;
	}

	var tpStartHour = parseInt(PF('startTimeWidget').getHours());
	var tpStartMinute = parseInt(PF('startTimeWidget').getMinutes());

	// Check if proposed hour is after selected start time hour
	if (parseInt(hour) > tpStartHour) {
		return true;
	}

	// Check if proposed hour is equal to selected start time hour and minutes
	// is after
	if ((parseInt(hour) == tpStartHour) && (parseInt(minute) > tpStartMinute)) {
		return true;
	}

	// if minute did not match, it can not be selected
	return false;
}

// not listener event key press
$(".startTime").on('keypress', function(ev) {
	return false;
});

// not listener event key press
$(".endTime").on('keypress', function(ev) {
	return false;
});

function tpCloseStartTime() {
	if (!PrimeFaces.widgets['startTimeWidget'] || !PrimeFaces.widgets['endTimeWidget']) {
		return false;
	}
	
	var tpStartHour = parseInt(PF('startTimeWidget').getHours());
	var tpStartMinute = parseInt(PF('startTimeWidget').getMinutes());
	
	PF('endTimeWidget').setTime((tpStartHour + 1) + ":" + tpStartMinute);
}

// add locale japanese for time picker
PrimeFacesExt.locales.TimePicker['ja'] = {
	hourText : '時間',
	minuteText : '分',
	amPmText : [ '午前', '午後' ],
	closeButtonText : '閉じる',
	nowButtonText : '現時',
	deselectButtonText : '選択解除'
};