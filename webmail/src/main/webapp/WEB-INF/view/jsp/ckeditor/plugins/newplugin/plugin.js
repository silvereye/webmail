(function() {
	CKEDITOR.plugins.add( 'newplugin',
	{
		init: function( editor )
		{
			editor.addCommand( 'newplugin',
			{
				exec : function( editor )
				{
					//var timestamp = new Date();    editor.insertHtml( 'The current date and time is: <em>' + timestamp.toString() + '</em>' );
					uploadImgCk();
				}
			});
			editor.ui.addButton && editor.ui.addButton( 'newplugin',
			{
				label:'Insert Image',command:'newplugin',icon:this.path + 'newplugin.png'
			});
		}
	});
})();