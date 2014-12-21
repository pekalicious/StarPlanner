$(document).ready(function() {
	$("a[rel^='prettyPhoto']").prettyPhoto({
		overlay_gallery: false
	});
	$("#version_overlay").hide();
	$("#starplanner_install_button").click(function(event) {
		$("#version_overlay").show();
		$("#page").fadeOut();
		//alert("Hello, world!");
		return false;
	});
	$(".installer").click(function(event){
		$("#version_overlay").hide();
		$("#page").fadeIn();
	});
	$("#cancel_overlay").click(function(event){
		$("#version_overlay").hide();
		$("#page").fadeIn();
	});
});
