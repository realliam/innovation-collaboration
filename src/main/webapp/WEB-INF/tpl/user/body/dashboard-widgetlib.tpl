<div id="widgetLib" class="dashboard-widgetbin">
<p id="widgetLibTitle">choose your panels to display</p>
        <#list widgetLibPanels as panel >
		    <#include "dashboardPanels/dashboard-panel.tpl">
		</#list>
</div>