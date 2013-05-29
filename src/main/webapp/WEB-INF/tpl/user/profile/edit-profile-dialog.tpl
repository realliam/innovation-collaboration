<#if (!((currentUser.displayName)??) || currentUser.displayName=="")>
	<#assign placeHolderUserName = "Click to enter your display name">
<#else>
	<#assign placeHolderUserName = currentUser.displayName>
</#if>

<div id="edit-my-profile" style="display:none;" title="Edit Profile">

<div class="profile-info-panel">
	<em style="top: -65px;">Update successfully!</em>
	<fieldset class="profile-desc-box">
		<legend class="profile-desc-title">My Profile</legend>
		<p>You only need to click on the information text or icon to edit you profile details.</p>
		<br/>
		<hr/>
		<ul>
			<li class="profile-item">
				<label aim="input">${placeHolderUserName}</label>
				<input style="display:none;" class="dialog-input-text" id="profile-username" name="profile-username" type="text" value='${(currentUser.displayName)!""}' placeholder='${placeHolderUserName}'/>
			</li>
			<li class="profile-item">
				<label aim="selector">
					<#if userGender = "m">
						<image id="genderIcon" class="gender-icon" src="/resources/style/img/male_icon.png">
						<#elseif userGender = "f">
						<image id="genderIcon" class="gender-icon" src="/resources/style/img/female_icon.png">
						<#else>
						<image id="genderIcon" class="gender-icon" src="/resources/style/img/na_icon.png">
					</#if>
				</label>
				<div id="gender-panel" style="display:none;">
					<ul>
						<li gender="m"><image class="gender-icon" src="/resources/style/img/male_icon.png"></li>
						<li gender="f"><image class="gender-icon" src="/resources/style/img/female_icon.png"></li>
						<li gender="n"><image class="gender-icon" src="/resources/style/img/na_icon.png"></li>
					</ul>
				</div>
				<input style="display:none;" class="dialog-input-text" id="profile-gender" name="profile-gender" type="hidden" value='${(currentUser.gender)!""}' placeholder='${(currentUser.gender)!"Click to enter your gender!"}'/>
			</li>
			<li class="profile-item">
				<label aim="input">${(currentUser.email)!"Click to enter your email!"}</label>
				<input style="display:none;" class="dialog-input-text" id="profile-email" name="profile-email" type="text" value='${(currentUser.email)!""}' placeholder='${(currentUser.email)!"Click to enter your email!"}'/>
			</li>
		</ul>
	</fieldset>

</div>

<div class="profile-image-panel">
	<div class="user-head-standard-box">
		<img id="head-image" class="user-head-standard" src="${userHead}">
	</div>
	<div class="operation-bar">
	<button id="upload-head-btn" class="operation-btn upload-btn" type="button">
		<i class="icon-trash icon-upload"></i>
		<span>Upload</span>
	</button>
	<button id="remove-head-btn" class="operation-btn remove-btn" type="button">
		<i class="icon-trash icon-remove"></i>
		<span>Delete</span>
	</button>
	<p sytle="clear:both"></p>
	<input id="fileupload" type="file" name="files" multiple>
	</div>
	<div id="progress" class="bar"></div>
</div>
<p style="clear:both;"></p>

</div>