cj("#editProfile").ready(function (){
	
	cj("#edit-my-profile").dialog(
		{
			autoOpen : false,
			show : {
				effect : 'drop',
				direction : "up"
			},
			hide : {
				effect : 'drop',
				direction : "up"
			},
			modal : true,
			closeOnEscape : true,
			minWidth : 600,
			position : "center"
		}
	);
	
	var isEditing = false;
	cj(".profile-item > label[aim=selector]").click(function(){
		if (!isEditing) {
			cj(this).hide();
			cj(this).parent().find("div").show("fade", "slow");
			cj(".profile-info-panel").find("em").animate({opacity : "hide", top : "-75"}, "fast");
			isEditing = true;
		}
	});
	cj("#gender-panel li").click(function(){
		if (isEditing) {
			cj("#gender-panel").hide();
			var genderVal = cj(this).attr("gender");
			var genderImg = cj("#genderIcon");
			var genderData = cj("#profile-gender");
			var summaryGender = cj(".user-profile .gender-icon");
			if (genderVal == "m" && genderData.val() != "m") {
				genderImg.attr("src","/resources/style/img/male_icon.png");
				summaryGender.attr("src","/resources/style/img/male_icon.png");
				genderData.attr("value", "m").change();
			} else if (genderVal == "f" && genderData.val() != "f") {
				genderImg.attr("src","/resources/style/img/female_icon.png");
				summaryGender.attr("src","/resources/style/img/female_icon.png");
				genderData.attr("value", "f").change();
			} else if (genderVal == "n" && genderData.val() != "n"){
				genderImg.attr("src","/resources/style/img/na_icon.png");
				summaryGender.attr("src","/resources/style/img/na_icon.png");
				genderData.attr("value", "").change();
			}
			cj("#gender-panel").parent().find("label").show("fade", "slow");
			isEditing = false;
		}
	});
	cj(".profile-item > label[aim=input]").click(function(){
		if (!isEditing) {
			cj(this).hide();
			cj(this).parent().find("input").show().focus();
			cj(".profile-info-panel").find("em").animate({opacity : "hide", top : "-75"}, "fast");
			isEditing = true;
		}
	});
	cj(".profile-item > input[type=text]").blur(function(){
		if (isEditing) {
			cj(this).hide();
			cj(this).parent().find("label").show("fade", "slow");
			isEditing = false;
		}
	});
	
	cj(".profile-item > input").change(function(){
		var profileUsername = cj("#profile-username");
		var profileGender = cj("#profile-gender");
		var profileEmail = cj("#profile-email");
		var summaryUsername = cj("#summaryUserName");
		var summaryEmail = cj("#summaryEmail");
		cj.ajax({
			type : "POST",
			url : "/asyncUser/updateProfile",
			data : "displayName=" + profileUsername.val() + "&" + "gender=" + profileGender.val() + "&" + "email=" + profileEmail.val(),
			success : function(response) {
				if (response.status == "SUCCESS") {
					cj(".profile-info-panel").find("em").html("Update successfully!");
					cj(".profile-info-panel").find("em").animate({opacity : "show",top : "-75"}, "slow");
					if (profileUsername.val() == "") {
						profileUsername.parent().find("label").html("Click to enter your display name!");
						profileUsername.attr("placeholder", "");
					} else {
						profileUsername.parent().find("label").html(profileUsername.val());
					}
					summaryUsername.html(profileUsername.val());
					profileEmail.parent().find("label").html(profileEmail.val());
					summaryEmail.html(profileEmail.val());
					setTimeout(function() {cj(".profile-info-panel").find("em").animate({opacity : "hide", top : "-75"}, "fast");}, 3000);
				} else {
					cj(".profile-info-panel").find("em").html("Update unsuccessfully!");
					cj(".profile-info-panel").find("em").animate({opacity : "show",top : "-75"}, "slow");
					setTimeout(function() {cj(".profile-info-panel").find("em").animate({opacity : "hide", top : "-75"}, "fast");}, 3000);
				}
			}
		});
	});
	
	//upload///////////////////
	
	cj("#upload-head-btn").click(function(){
		cj("#fileupload").click();
	});
	
	cj('#fileupload').fileupload({
		url: '/asyncUser/head',
        dataType: 'json',
        fileTypes: /^image\/(gif|jpeg|png)$/,
        maxFileSize: 20000000,
        done: function (e, data) {
        	cj("#head-image").attr("src", data.result[0].url);
        	cj(".user-profile-head .user-head-standard").attr("src", data.result[0].url);
        	cj(".user-head-icon").attr("src", data.result[0].url);
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            cj('#progress .bar').css(
                'width',
                progress + '%'
            );
        }
    }).bind('fileuploadfail', function (e, data) {alert(data);});
	
	cj("#remove-head-btn").click(function(){
		cj.ajax({
			type : "POST",
			url : "/asyncUser/removeHeadPhoto",
			success : function(response) {
				if (response.status == "SUCCESS") {
					cj("#head-image").attr("src", response.result);
		        	cj(".user-profile-head .user-head-standard").attr("src", response.result);
		        	cj(".user-head-icon").attr("src", response.result);
					cj(".profile-info-panel").find("em").animate({opacity : "show",top : "-75"}, "slow");
					setTimeout(function() {cj(".profile-info-panel").find("em").animate({opacity : "hide", top : "-75"}, "fast");}, 3000);
				} else {
					cj(".profile-info-panel").find("em").html(response.result).animate({opacity : "show",top : "-75"}, "slow");
					setTimeout(function() {cj(".profile-info-panel").find("em").animate({opacity : "hide", top : "-75"}, "fast");}, 3000);
				}
			}
		});
	});
	//upload////////////////////
});