/*
 * Copyright 2010, Wen Pu (dexterpu at gmail dot com)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * Check out http://www.cs.illinois.edu/homes/wenpu1/chatbox.html for document
 *
 * Depends on jquery.ui.core, jquery.ui.widiget, jquery.ui.effect
 *
 * Also uses some styles for jquery.ui.dialog
 *
 */


// TODO: implement destroy()
(function($) {
    $.widget("ui.chatbox",{
        options: {
            id: null, //id for the DOM element
            title: null, // title of the chatbox
            imagenm: null, //image for user status
            user: null, // can be anything associated with this chatbox
            hidden: false,
            offset: 0, // relative to right edge of the browser window
            width: 250, // width of the chatbox
            messageSent: function(id, user, msg) {
                // override this
            	/*if(msg.indexOf(":)")>=0)
            	{
            	msg=msg.replace(/:\)/g, "<img src='smiley/happy.gif' />");
            	}*/
                this.boxManager.addMsg(user.first_name, msg);
            },
            boxClosed: function(options) {
            	//alert(title);
            	//try{
            	  title =options.title || "No Title";
            	  id =options.id;
            	 // var title=title.replace('.','\\.');
            	var newchatid="#"+title.split("@")[0]+"chatboxcreated";
            	newchatid=newchatid.replace('.','\\.');
            	$(newchatid).remove();
            	
            	var opechatid="#"+title.split("@")[0]+"open_chat_box";
            	opechatid=opechatid.replace('.','\\.');
            	$(opechatid).remove();
            	
            	//var openid="#"+title.split("@")[0]+"open_chat_box";
    			//$('<div id="'+title.split("@")[0]+'chat_div"></div>').appendTo(openid);
    			//}catch(e){}
    			//alert('This Test !');
            }, // called when the close icon is clicked
            boxManager: {
                // thanks to the widget factory facility
                // similar to http://alexsexton.com/?p=51
                init: function(elem) {
                    this.elem = elem;
                },
                addMsg: function(peer, msg ,hid_nm) {
                    var self = this;
                    var box = self.elem.uiChatboxLog;
                    msg=setEmoji(msg)
                    	
                    var hid_arr=hid_nm.split("_div");
                    var hid_id=hid_arr[0]+"boxcreated";
                    var chk=$(hid_id).children('.new_header_top').children('span').children('.hid_chat_nm').html();
                    var e = document.createElement('div');
                    box.append(e);
                    $(e).hide();

                    var systemMessage = false;
                    
                    var para=document.createElement('p');

                    if (peer) {
                    	if(chk!=peer)
                    		{
                    		e.appendChild(para);
                    		var peerName = document.createElement("b");
                    		$(peerName).text(peer + ": ");
                    		para.appendChild(peerName);
                    		$(hid_id).children('.new_header_top').children('span').children('.hid_chat_nm').html("me");
                    		}
                    	else
                    		{
                    		e.appendChild(para);
                    		//var peerName = document.createElement("b");
                    		//$(peerName).text(peer + ": ");
                    		//para.appendChild(peerName);
                    		}
                    	
                    } else {
                        systemMessage = true;
                    }
                    var msgElement = document.createElement(
                        systemMessage ? "i" : "span");
                    $(msgElement).html(msg);
                    para.appendChild(msgElement);
                    $(e).addClass("ui-chatbox-msg");
                    $(e).css("maxWidth", $(box).width());
                    $(e).fadeIn();
                    self._scrollToBottom();

                    if (!self.elem.uiChatboxTitlebar.hasClass("ui-state-focus")
                        && !self.highlightLock) {
                        self.highlightLock = true;
                        self.highlightBox();
                    }
                },
                highlightBox: function() {
                    var self = this;
                    self.elem.uiChatboxTitlebar.effect("highlight", {}, 300);
                    self.elem.uiChatbox.effect("bounce", {times: 3}, 300, function() {
                        self.highlightLock = false;
                        self._scrollToBottom();
                    });
                },
                toggleBox: function() {
                    this.elem.uiChatbox.toggle();
                },
                _scrollToBottom: function() {
                    var box = this.elem.uiChatboxLog;
                    box.scrollTop(box.get(0).scrollHeight);
                }
            }
        },
        toggleContent: function(event) {
            this.uiChatboxContent.toggle();
            if (this.uiChatboxContent.is(":visible")) {
                this.uiChatboxInputBox.focus();
            }
        },
        widget: function() {
            return this.uiChatbox
        },
        _create: function() {       	
          //	alert('Hi=5');
        
            var self = this,
            options = self.options,
            title = options.title || "No Title";
            imagenm = options.imagenm;
            // chatbox
         //   alert(title);
            title=title.replace("_",".");
            var title_length = title.split("@");
         // alert(title_length.length);
            
            
            
            
            uiChatbox = (self.uiChatbox = $('<div id="'+title.split("@")[0]+'chatboxcreated"></div>'))
                .appendTo($('.inner_chat_box'))
                .addClass('ui-widget ' +
                          'ui-corner-top ' +
                          'ui-chatbox' + ' new_chat_header'
                         )
                .attr('outline', 0)
                .focusin(function() {
                    // ui-state-highlight is not really helpful here
                    //self.uiChatbox.removeClass('ui-state-highlight');
                    self.uiChatboxTitlebar.addClass('ui-state-focus');
                    
                })
                .focusout(function() {
                    self.uiChatboxTitlebar.removeClass('ui-state-focus');
                   // alert('By');
                }),
            // titlebar
                //alert("js="+imagenm);
            uiChatboxTitlebar = (self.uiChatboxTitlebar = $('<div></div>'))
                .addClass('ui-widget-header ' +
                          'ui-corner-top ' +
                          'ui-chatbox-titlebar ' +
                          'ui-dialog-header' + ' new_header_top' // take advantage of dialog header style
                         )
                .click(function(event) {
                    self.toggleContent(event);
                  //  alert('Hi_1')
                })
                .appendTo(uiChatbox),
            uiChatboxTitle = (self.uiChatboxTitle = $('<span></span>'))
                .html("<div class='hid_chat_nm'></div><div class='hid_frnd_uid'></div><div id='"+title.split("@")[0]+"avlblimg' style='float: left;margin-top: 7px;width: 15px;'>" +
                		"<img src='"+imagenm+"' class='chat_box_icon' /></div>"+"<div class='title_name'>"+title+"</div>")
                .appendTo(uiChatboxTitlebar),
            uiChatboxTitlebarClose = (self.uiChatboxTitlebarClose = $('<a></a>'))
                .addClass('ui-corner-all ' +
                          'ui-chatbox-icon '+ 'chat_minu'
                         )
                .attr('role', 'button')
                .hover(function() { uiChatboxTitlebarClose.addClass('ui-state-hover'); },
                       function() { uiChatboxTitlebarClose.removeClass('ui-state-hover'); })
                .click(function(event) {
                    uiChatbox.hide();
                   // alert('Hi');
                   // alert('Hi_2');
                  
                    self.options.boxClosed(self.options);
                    return false;
                })
                .appendTo(uiChatboxTitlebar),
            uiChatboxTitlebarCloseText = $('<span></span>')
                .addClass('ui-icon ' +
                          'ui-icon-closethick' + ' new_delete_chat')
                .text('x')// TEST chnages text close
                .appendTo(uiChatboxTitlebarClose),
            uiChatboxTitlebarMinimize = (self.uiChatboxTitlebarMinimize = $('<a ></a>'))
                .addClass('ui-corner-all ' +
                          'ui-chatbox-icon '+ 'chat_minu'
                         )
                .attr('role', 'button')
                .hover(function() { uiChatboxTitlebarMinimize.addClass('ui-state-hover'); },
                       function() { uiChatboxTitlebarMinimize.removeClass('ui-state-hover'); })
                .click(function(event) {
                    self.toggleContent(event);
                    $(this).parent().toggleClass('chat_input_new' );
                   $(this).parent().parent().toggleClass( 'chat_input');
                    
                   
                  //  alert('Hi-Hi');
                   // alert('Hi-3');
                    return false;
                })
                .appendTo(uiChatboxTitlebar),
            uiChatboxTitlebarMinimizeText = $('<span></span>')
                .addClass('ui-icon ' +
                          'ui-icon-minusthick ' 
                		+ 'minustick_1')
                .text('-')// TEST CHNAGES minimize
                .appendTo(uiChatboxTitlebarMinimize),
            // content
            uiChatboxContent = (self.uiChatboxContent = $('<div></div>'))
                .addClass('ui-widget-content ' +
                          'ui-chatbox-content '
                         )
                .appendTo(uiChatbox),
            uiChatboxLog = (self.uiChatboxLog = self.element)
                .addClass('ui-widget-content ' + 
                          'ui-chatbox-log'
                         )
                .appendTo(uiChatboxContent),
            uiChatboxInput = (self.uiChatboxInput = $('<div></div>'))
                .addClass('ui-widget-content ' +
                          'ui-chatbox-input'
                         )
                .click(function(event) {
                	//self.parent().parent().toggleClass('chat_input_new' );
                	//alert('Hi-Hi-1');
                	//$('div.chat_input').removeClass('chat_input');
                    // anything?
                })
                .appendTo(uiChatboxContent),
            uiChatboxInputBox = (self.uiChatboxInputBox = $('<textarea></textarea>'))
                .addClass('ui-widget-content ' +
                          'ui-chatbox-input-box ' +
                          'ui-corner-all' + ' chat_textarea_input'
                         )
                .appendTo(uiChatboxInput)
                .keydown(function(event) {
                    if (event.keyCode && event.keyCode == $.ui.keyCode.ENTER) {
                        msg = $.trim($(this).val());
                       var sarr=title.split("@");
                       var frnduid=sarr[0];
                       frnduid=frnduid.replace('.','\\.');
                        frnduid=frnduid+"chatboxcreated";
                      var sendid=  $("#"+frnduid).children('.new_header_top').children('span').children('.hid_frnd_uid').html();
                      //  alert(msg);
                        if (msg.length > 0) {
                            self.options.messageSent(self.options.id, self.options.user, msg);
                            $.ajax ({
                            	url:"sendChatMessage",
                            	data: {
                            		message:msg,
                            		buddyJID:sendid,
                            	},
                            	success: function(data){
                            		//alert(data);
                            	}
                            });
                        }
                        $(this).val('');
                        return false;
                    }
                })
                .focusin(function() {
                    uiChatboxInputBox.addClass('ui-chatbox-input-focus');
                    var box = $(this).parent().prev();
                    box.scrollTop(box.get(0).scrollHeight);
                })
                .focusout(function() {
                    uiChatboxInputBox.removeClass('ui-chatbox-input-focus');
                });

            // disable selection
            uiChatboxTitlebar.find('*').add(uiChatboxTitlebar).disableSelection();

            // switch focus to input box when whatever clicked
            uiChatboxContent.children().click(function() {
                // click on any children, set focus on input box
                self.uiChatboxInputBox.focus();
                //alert('Hi_4');
            });

            self._setWidth(self.options.width);
            self._position(self.options.offset);

            self.options.boxManager.init(self);

            if (!self.options.hidden) {
                uiChatbox.show();
            }
        },
        _setOption: function(option, value) {
            if (value != null) {
                switch (option) {
                case "hidden":
                    if (value)
                        this.uiChatbox.hide();
                    else
                        this.uiChatbox.show();
                    break;
                case "offset":
                    this._position(value);
                    break;
                case "width":
                    this._setWidth(value);
                    break;
                }
            }
            $.Widget.prototype._setOption.apply(this, arguments);
        },
        _setWidth: function(width) {
            this.uiChatboxTitlebar.width(width + "px");
            this.uiChatboxLog.width(width + "px");
            this.uiChatboxInput.css("maxWidth", width + "px");
            // padding:2, boarder:2, margin:5
            this.uiChatboxInputBox.css("width", (width - 18) + "px");
        },
        _position: function(offset) {
            //this.uiChatbox.css("right", offset);
        	this.uiChatbox.css("float", "right");
        }
    });
}(jQuery));





				/// For Chat Option Only Started Here 
$(document).ready(function() {
	
	
	$('.chat_inner_content >.cheat_row').click(function(){
		
		
	//	alert('Hi');
		
		
	});
	
	
	
	
	
	var removeClass="false";
	// MINIMIZE BOX

	// HIDE ONCLICK BODY
	$("html").click(function () {
        if (removeClass) 
		      {
              	$('.overflow_info_content').css('display','none');	
            }
            removeClass = true;
           });
	
		// CHAT TAB STARED HERE 
	var countOffOverflow = 0;
	$('.cheat_row').click(function(){
		var x =$(window).width();
		//alert(x);
		var main_chat_width = $('.inner_chat_box').innerWidth()+500;
		var innerbox = x-470;
		var main_chat_div = innerbox + 187;
		//alert(main_chat_div);
		$('.main_chat_box').css('max-width',main_chat_div);
		$('.main_inner_box').css('max-width',main_chat_div);
		$('.inner_chat_box').css('max-width',innerbox);
		//alert(innerbox);
	    //alert(innerbox);
		//alert(main_chat_width);
		$(document).on('click', '.overflow_close', function(){ 
			$(this).parent().remove();
			$('.overflow_info_content').css('display','block');	
			removeClass = false;
	  	}); 
		if(main_chat_width > x)
			{
			  countOffOverflow++;
		      uiChatbox.addClass('new_overflow');
		      uiChatbox.addClass('outoff_overflow_'+ countOffOverflow);
		     }
		else{
			}
		});
	$('.chat_mail').click(function(){
		var x =$(window).width();
		//alert(x);
		var main_chat_width = $('.inner_chat_box').innerWidth()+500;
		var innerbox = x-470;
		var main_chat_div = innerbox + 187;
//		alert(main_chat_div);
		$('.main_chat_box').css('max-width',main_chat_div);
		
		$('.main_inner_box').css('max-width',main_chat_div);
		$('.inner_chat_box').css('max-width',innerbox);
		//alert(innerbox);
	   //   alert(innerbox);
		//alert(main_chat_width);
		$(document).on('click', '.overflow_close', function(){ 
			$(this).parent().remove();
			$('.overflow_info_content').css('display','block');	
			removeClass = false;
	  	}); 
		
		
		
		if(main_chat_width > x)
			{
			countOffOverflow++;
			//alert(countOffOverflow);
		      //$('.overflow_chat').append();
		   /*   $('.overflow_chat').css('display','block');
		      $('.overflow_info_bottom').css('display','block');
		      $('.overflow_info_content').append('<div class="small_row"><span>Hariom Srivastava</span><div class="overflow_close"></div></div>');
		      var count_info_overflow =   $('.small_row').length;
		    // alert(count_info_overflow);
		      $('.count_overflow').text(count_info_overflow );*/	
		      uiChatbox.addClass('new_overflow');
		      uiChatbox.addClass('outoff_overflow_'+ countOffOverflow);
		     
		       
			}
		else{
			//$('.overflow_chat').css('display','none');
			//alert('This is Underflow');
			//$('.ui-widget').removeClass('new_overflow');
			}
		});

	
	
	/// CHAT HIDE OVERFLOW CONTENT
	$('.overflow_info_bottom').click(function(){
		
		
		    if($('.overflow_info_content').css('display')=='none')
		    {
		  //  alert('Hi')
		    	$('.overflow_info_content').css('display','block');	
		    	removeClass = false;
		    }
		    else
		    {
		    //	 alert('By')
		    	$('.overflow_info_content').css('display','none');	
		    
		    	
		    }
		
		
	});
	});
	
