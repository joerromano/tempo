<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="/css/normalize.css">
    <link rel="stylesheet" href="/css/html5bp.css">
      
      <!-- BOOTSTRAP: Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
      
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

      
    <link rel="stylesheet" href="/css/main.css">
  </head>
  <body>
      
      <div class="navbar-wrapper">
      <div class="container">

        <nav class="navbar navbar-inverse navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="/home">Tempo</a>
            </div>
            ${navbar}
          </div>
        </nav>

      </div>
    </div>
      
     ${content}
      <br>
      <footer class="footer">
          <div class="container" style="padding: 15px;">
            <p class="text-muted">This is the footer information. Coming soon.</p>
          </div>
      </footer>

      <!-- Moment.js for TIME -->
      <script src="/js/moment.min.js"></script>

     <!-- Again, we're serving up the unminified source for clarity. -->
      <script src="/js/jquery-2.1.1.js"></script>
      
      <script src="/js/jquery.scrollTo.min.js"></script>
      
      <!-- BOOTSTRAP: Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
      
      <!-- jQuery UI -->
      <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
      
      <script src="/js/jquery.ui.touch-punch.min.js"></script>
      
       <script src="/js/main.js"></script>
      ${scripts}
  </body>
</html>
