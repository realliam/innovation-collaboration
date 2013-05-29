(function($){
cj('#userDashboard').ready(function(){
	var openState = false;
	
	cj("#dashboardSummaryToggler").click(function(){
		if (!openState) {
			cj("#dashboardSummary").show("fold", {mode:"show"}, 1500);
			openState = true;
		} else {
			cj("#dashboardSummary").hide("fold", {mode:"hide"}, 1500);
			openState = false;
		}
	});
	
	cj( "#accordion" )
	.accordion({
		header: "> div > h3",
		autoHeight: false,
		navigation: true,
		collapsible: true,
		active: false,
		changestart: function( event, ui ){
			var username = cj("#accordion").attr("user");
			var summaryUsername = cj("#summaryUserName");
			var summaryEmail = cj("#summaryEmail");
			var summaryTeams = cj("#summaryTeams");
			var summaryGender = cj(".user-profile .gender-icon");
			if (ui.newHeader.attr('title') == 'profile') {
				cj.ajax({
					type : "POST",
					url : "/asyncUser/user/"+ username,
					success : function(response) {
						summaryUsername.html(response.result.displayName);
						summaryEmail.html(response.result.email);
						summaryTeams.html(response.result.teams);
						if (response.status == "SUCCESS") {
							if (response.result.gender == "m") {
								summaryGender.attr("src","/resources/style/img/male_icon.png");
							} else if (response.result.gender == "f") {
								summaryGender.attr("src","/resources/style/img/female_icon.png");
							} else if (response.result.gender == "n"){
								summaryGender.attr("src","/resources/style/img/na_icon.png");
							}
						}
					}
				});
			}
		}
	}).sortable({
		axis: "y",
		handle: "h3",
		stop: function( event, ui ) {
			// IE doesn't register the blur when sorting
			// so trigger focusout handlers to remove .ui-state-focus
			ui.item.children( "h3" ).triggerHandler( "focusout" );
		}
	});
	
});

})(cj);

