function accessibleAlert(text) {
	setTimeout(function () {
		var divAlert = jQuery("#divAlerta");
		if (divAlert.length > 0) { divAlert.remove(); }
		divAlert = jQuery('<div id="divAlerta" class="element-invisible" role="alert"></div>');
		divAlert.html(text);
		jQuery("body").append(divAlert);
		setTimeout(function () { jQuery("#divAlerta").remove(); }, 1000);
	}, 1);
}

function agregaTitulo(titulo, prefijo, incluirSpanOculto) {
	if (prefijo == null) prefijo = undefined;
	if (incluirSpanOculto === undefined) incluirSpanOculto = true;
	if (titulo !== undefined) {
		return (incluirSpanOculto ? '<span class="element-invisible">' : '') +
			(prefijo !== undefined ? ' ' + prefijo + ' ' : '') +
			'"' + titulo + '"' + (incluirSpanOculto ? '</span>' : '');
	}
	else {
		return '';
	}
}

function getTiempo(duracion)
{
	if (duracion == 0) return "0 segundos";
	var resultado = new Array();
	if (duracion > 3600)
	{
		var horas = parseInt(duracion  / 3600);
		resultado.push(horas.toString() + " hora" + (horas > 1 ? "s" : ""));
		duracion %= 3600;
	}
	if (duracion > 60)
	{
		var minutos = parseInt(duracion / 60);
		resultado.push(minutos.toString() + " minuto" + (minutos > 1 ? "s" : ""));
		duracion %= 60;
	}
	if (duracion > 0)
	{
		resultado.push(parseInt(duracion).toString() + " segundo" + (duracion > 1 ? "s" : ""));
	}
	return (resultado.length > 1 ? resultado.slice(0, resultado.length - 1).join(', ') + ' y ' + resultado[resultado.length - 1] : resultado[0]);
}

function actualizaPosicion(pl, iframeId)
{
	if (pl.getPlayerState() != YT.PlayerState.PLAYING && jQuery(document).data("interval_" + iframeId) !== undefined)
	{
		clearInterval(jQuery(document).data("interval_" + iframeId));
		jQuery(document).removeData("interval_" + iframeId);
		return;
	}
	var posicion = pl.getCurrentTime();
	jQuery("#ddPosicion_" + iframeId).html(getTiempo(posicion));
}


function onYouTubeIframeAPIReady() {
	var player;
	jQuery("iframe[src*='youtube.com/embed/']").each(function (index, value) {
		var ifr = jQuery(value);
		if (ifr.attr("id") == undefined) ifr.attr("id", "youtubeVideoIframe" + index);
		player = new YT.Player(ifr.attr("id"), {
			events: {
				'onReady': onPlayerReady
				// 'onStateChange': onPlayerStateChange
			}
		});
		// alert("Player preparado!");
	});
}

function onPlayerReady(event) {
	// event.target.playVideo();
	var ifr = jQuery(event.target.getIframe());
	var url = event.target.getVideoUrl();
	var m = /(^.*?v=)(.*)($|&)/gi.exec(url);
	var videoId = m[2];
	var titulo = "";
	if (ifr.attr("data-title") !== undefined) {
		agregaControlesAccesibles(event.target, ifr.attr("id"), ifr.attr("data-title"));
	}
	else {
		jQuery.support.cors = true;
		jQuery.getJSON('youtube-title.php?id=' + videoId)
		.done(function (data) {
			var t = data.entry.title.$t;
			agregaControlesAccesibles(event.target, ifr.attr("id"), t);
		})
		.fail(function (jqXHR, textStatus, errorThrown) {
			alert("¡Error al obtener el título para el vídeo " + videoId + "! Mensaje: " + textStatus + ". Mensaje de error: " + errorThrown);
			if (ifr.attr("data-title") !== undefined) agregaControlesAccesibles(event.target, ifr.attr("id"), ifr.attr("data-title"));
			else agregaControlesAccesibles(event.target, ifr.attr("id"), undefined);
		});
	}
}

function agregaControlesAccesibles(pl, iframeId, titulo) {
	var ifr = jQuery("#" + iframeId);
	var btnPlay = jQuery('<button type="button" class="btnPlayerControl" id="btnPlay_' + iframeId + '">' + agregaTitulo(titulo, playText) + '</button>');
	var btnMute = jQuery('<button type="button" class="btnPlayerControl" id="btnMute_' + iframeId + '">' + agregaTitulo(titulo, muteText) + '</button>');
	var btnVolumeDown = jQuery('<button type="button" class="btnPlayerControl" id="btnVolumeDown_' + iframeId + '">' + agregaTitulo(titulo, volDownText) + '</button>');
	var btnVolumeUp = jQuery('<button type="button" class="btnPlayerControl" id="btnVolumeUp_' + iframeId + '">' + agregaTitulo(titulo, volUpText) + '</button>');
	var btnFw30Secs = jQuery('<button type="button" class="btnPlayerControl" id="btnFw30Secs_' + iframeId + '">' + agregaTitulo(titulo, fwdText) + '</button>');
	var btnBw30Secs = jQuery('<button type="button" class="btnPlayerControl" id="btnBw30Secs_' + iframeId + '">' + agregaTitulo(titulo, rewText) + '</button>');
	var dlInfo = jQuery('<dl class="element-invisible" id="dl_' + iframeId + '"></dl>');
	var dtPosicion = jQuery('<dt>' + positionText + ':</dt>');
	var ddPosicion = jQuery('<dd id="ddPosicion_' + iframeId + '">0 ' + secondsText + '</dd>');
	var dtDuracion = jQuery('<dt>' + durationText + ':</dt>');
	var ddDuracion = jQuery('<dd id="ddDuracion_' + iframeId + '">' + unknownText + '</dd>');
	var LnkSaltarFlash = jQuery('<a href="#saltar_' + iframeId + '" class="element-invisible">' + agregaTitulo(titulo, skipVideoText, false) + '</a>');
	var lnkUrl = jQuery('<a target="_blank" class="element-invisible" href="' + pl.getVideoUrl() + '">' + agregaTitulo(titulo, watchYtText, true) + '<span class="element-invisible"> (' + openNewWindowText + ')</span></a>');
	var pLnkUrl = jQuery("<p></p>");
	pLnkUrl.append(lnkUrl);
	var ul = jQuery("<ul></ul>"), liPlay = jQuery('<li class="liControl"></li>'), liMute = jQuery('<li class="liControl"></li>'), liVolumeDown = jQuery('<li class="liControl"></li>'), liVolumeUp = jQuery('<li class="liControl"></li>'), liFw30Secs = jQuery('<li class="liControl"></li>'), liBw30Secs = jQuery('<li class="liControl"></li>');
	ul.addClass("element-invisible");
	var contenedor = jQuery('<div>').addClass('reproductor-accesible');
	ifr.before(contenedor);
	//ifr.before(ul);
	contenedor.append(ul);
	dlInfo.append(dtPosicion);
	dlInfo.append(ddPosicion);
	dlInfo.append(dtDuracion);
	dlInfo.append(ddDuracion);
	ul.after(dlInfo);
	dlInfo.after(LnkSaltarFlash);
	ifr.after(jQuery('<a name="saltar_' + iframeId + '"></a>'));
	ifr.attr("tabindex", "-1");
	liPlay.append(btnPlay);
	ul.append(liPlay);
	liVolumeDown.append(btnVolumeDown);
	ul.append(liVolumeDown);
	liVolumeUp.append(btnVolumeUp);
	ul.append(liVolumeUp);
	liMute.append(btnMute);
	ul.append(liMute);
	liFw30Secs.append(btnFw30Secs);
	ul.append(liFw30Secs);
	liBw30Secs.append(btnBw30Secs);
	ul.append(liBw30Secs);
	dlInfo.after(pLnkUrl);
	window["onPlayerStateChange_" + iframeId] = function (event) {
		var state, txtBtnPlay, txtBtnMute, txtAlert = "";
		state = event.data;
		if (jQuery(document).data("lastState_" + iframeId) !== undefined && jQuery(document).data("lastState_" + iframeId) == state) return;
		// si el estado no cambia aunque se ejecute el evento, no hacemos nada.

		jQuery(document).data("lastState_" + iframeId, state);

		switch (state) {
			case YT.PlayerState.ENDED:
				// btnBw30Secs.attr("disabled", "disabled");
				// btnFw30Secs.attr("disabled", "disabled");
				txtAlert = finishedText;
				jQuery("#ddPosicion_" + iframeId).html("0 " + secondsText);
				break;
			case YT.PlayerState.PLAYING:
				var duracion = pl.getDuration();
				if (duracion == 0)
				{
					jQuery("#ddDuracion_" + iframeId).html(unknownText);
				}
				else
				{
					jQuery("#ddDuracion_" + iframeId).html(getTiempo(duracion));
				}
				if (jQuery(document).data("interval_" + iframeId) === undefined)
				{
					jQuery(document).data("interval_" + iframeId, setInterval(function() { actualizaPosicion(pl, iframeId); }, 1000));
				}
				txtAlert = playingText;
				// btnBw30Secs.removeAttr("disabled");
				// btnFw30Secs.removeAttr("disabled");
				break;
			case YT.PlayerState.PAUSED:
				txtAlert = pausedText;
				break;
			case YT.PlayerState.BUFFERING:
				txtAlert = loadingText;
				break;
		}
		if (txtAlert != "") accessibleAlert(txtAlert);
		switch (state) {
			case -1: case 0: case 2:
				txtBtnPlay = playText;
				break;
			default:
				txtBtnPlay = pauseText;
				break;
		}
		jQuery("#btnPlay_" + iframeId).html(agregaTitulo(titulo, txtBtnPlay));
	};
	pl.addEventListener("onStateChange", "onPlayerStateChange_" + iframeId);
	jQuery("button.btnPlayerControl").on("focus", function () { jQuery(this).parents(":eq(1)").removeClass("element-invisible"); });
	jQuery("button.btnPlayerControl").on("blur", function () { jQuery(this).parents(":eq(1)").addClass("element-invisible"); });
	lnkUrl.on("focus", function () { jQuery(this).removeClass("element-invisible"); });
	lnkUrl.on("blur", function () { jQuery(this).addClass("element-invisible"); });
	btnPlay.on("click", function () {
		var playerState = pl.getPlayerState();
		if (playerState == 1)
		{
			pl.pauseVideo();
			$(this).css("background", "url(img/ico_reproductor/play.jpg) no-repeat scroll center bottom #1B1B1B");
		}
		else
		{
			pl.playVideo();
			$(this).css("background", "url(img/ico_reproductor/pause.jpg) no-repeat scroll center bottom #1B1B1B");			
		}
	});
	btnMute.on("click", function () {
		if (pl.isMuted()) {
			accessibleAlert(soundActivatedText);
			pl.unMute();
		}
		else {
			accessibleAlert(soundDeactivatedText);
			pl.mute();
		}
	});
	btnVolumeUp.on("click", function () {
		var vActual = pl.getVolume(), nuevoVolumen;
		if (vActual >= 100) {
			accessibleAlert(volumeMaxText);
		}
		else {
			nuevoVolumen = vActual + (vActual + 5 > 100 ? (100 - vActual) : 5);
			accessibleAlert(volumeAdjustText + " " + nuevoVolumen.toString() + "%.");
			pl.setVolume(nuevoVolumen);
		}
	});
	btnVolumeDown.on("click", function () {
		var vActual = pl.getVolume();
		var nuevoVolumen;
		if (vActual <= 0) {
			accessibleAlert(volumeMinText);
		}
		else {
			nuevoVolumen = vActual - (vActual - 5 < 0 ? vActual : 5)
			accessibleAlert(volumeAdjustText + " " + nuevoVolumen.toString() + "%.");
			pl.setVolume(nuevoVolumen);
		}
	});
	btnFw30Secs.on("click", function() {
		var pos = pl.getCurrentTime(), duracion = pl.getDuration();
		var nuevaPos = pos + 30 > duracion ? duracion : pos + 30;
		pl.seekTo(nuevaPos, true);
		accessibleAlert(positionText + ": " + getTiempo(pos + 30) + ".");
	});
	btnBw30Secs.on("click", function () {
		var pos = pl.getCurrentTime(), duracion = pl.getDuration();
		var nuevaPos = (pos - 30 < 0 ? 0 :  pos - 30);
		pl.seekTo(nuevaPos, true);
		accessibleAlert(positionText + ": " + getTiempo(nuevaPos) + ".");
	});
}

jQuery(function () {
	jQuery("iframe[src*='youtube.com/embed/']").each(function (index, value) {
		var ifr = jQuery(value);
		if (ifr.attr("data-title") === undefined) ifr.attr("data-title", (index + 1).toString() + " de Youtube");
	});
});