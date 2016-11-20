
 
 $( document ).ready(function() {
   // alert( "Handler for load called." );
    var form = document.getElementById('file-form');
    var fileSelect = document.getElementById('uploadedFilename');
    var fileSelectDecomp = document.getElementById('uploadedFilenameDecomp');
    var uploadButton = document.getElementById('submitbtn');
    var fileName = document.getElementById('fileName');
    var fileNameDecomp = document.getElementById('fileNameDecomp');
    var uploadStatus = document.getElementById('uploadStatus');

$( "#uploadedFilename" ).change(function() {
    var files = fileSelect.files;
    fileName.innerHTML = files[0].name;
});
//displays filename for decompression
$( "input#uploadedFilenameDecomp" ).change(function() {
    var files = fileSelectDecomp.files;
    fileNameDecomp.innerHTML = files[0].name;
});
 $('#file-form').on('submit', function(e){
        e.preventDefault();
    var files = fileSelect.files;
    var formData = new FormData();
    formData.append('uploadedFilename', files[0], files[0].name);



    var xhr = new XMLHttpRequest();
    xhr.open('POST', './bin/uploadAndConvert.php', true);
    //alert(window.location.pathname);
    xhr.onload = function () {
      if (xhr.status === 200) {
    // File(s) uploaded.
    //alert("uploaded");
      uploadButton.innerHTML = 'Upload';
       } else {
        alert('An error occurred!');
       }
    };

    xhr.send(formData);

    var times; 

  //This ajax request takes gives the file name to the server, which then executes the scripts 
  $.ajax({
        url: "bin/index.php?name="+files[0].name,
        success: function (result) {
            times = result;
        },
        async: false
    });

    alert(times);
    handleAuthClick(event);

  });


});

//BELOW IS THE GOOGLE CALENDAR AUTH JAVASCRIPT
      // Your Client ID can be retrieved from your project in the Google
      // Developer Console, https://console.developers.google.com
      var CLIENT_ID = '1004394191326-i0fm8ehp6aq69at9tqbr0f6rclem19nl.apps.googleusercontent.com';

      var SCOPES = ["https://www.googleapis.com/auth/calendar"];

      /**
       * Check if current user has authorized this application.
       */
      function checkAuth() {
        gapi.auth.authorize(
          {
            'client_id': CLIENT_ID,
            'scope': SCOPES.join(' '),
            'immediate': true
          }, handleAuthResult);
      }

      /**
       * Handle response from authorization server.
       *
       * @param {Object} authResult Authorization result.
       */
      function handleAuthResult(authResult) {
        var authorizeDiv = document.getElementById('authorize-div');
        if (authResult && !authResult.error) {
          // Hide auth UI, then load client library.
          authorizeDiv.style.display = 'none';
          loadCalendarApi();
        } else {
          // Show auth UI, allowing the user to initiate authorization by
          // clicking authorize button.
          authorizeDiv.style.display = 'inline';
        }
      }

      /**
       * Initiate auth flow in response to user clicking authorize button.
       *
       * @param {Event} event Button click event.
       */
      function handleAuthClick(event) {
        gapi.auth.authorize(
          {client_id: CLIENT_ID, scope: SCOPES, immediate: false},
          handleAuthResult);
        return false;
      }

      /**
       * Load Google Calendar client library. List upcoming events
       * once client library is loaded.
       */
      function loadCalendarApi() {
        gapi.client.load('calendar', 'v3', listUpcomingEvents);
      }

      /**
       * Print the summary and start datetime/date of the next ten events in
       * the authorized user's calendar. If no events are found an
       * appropriate message is printed.
       */
      function listUpcomingEvents() {
        var event = {
              'summary': 'Google I/O 2015',
        'location': '800 Howard St., San Francisco, CA 94103',
        'description': 'A chance to hear more about Google\'s developer products.',
        'start': {
          'dateTime': '2016-11-19T09:00:00-07:00',
          'timeZone': 'America/Los_Angeles'
        },
        'end': {
          'dateTime': '2016-11-19T17:00:00-07:00',
          'timeZone': 'America/Los_Angeles'
        },
        'recurrence': [
          'RRULE:FREQ=DAILY;COUNT=2'
        ],
        'attendees': [
          {'email': 'lpage@example.com'},
          {'email': 'sbrin@example.com'}
        ],
        'reminders': {
          'useDefault': false,
          'overrides': [
            {'method': 'email', 'minutes': 24 * 60},
            {'method': 'popup', 'minutes': 10}
          ]
        }
      };

var request = gapi.client.calendar.events.insert({
  'calendarId': 'primary',
  'resource': event
});

request.execute(function(event) {
  appendPre('Event created: ' + event.htmlLink);
});


        var request = gapi.client.calendar.events.list({
          'calendarId': 'primary',
          'timeMin': (new Date()).toISOString(),
          'showDeleted': false,
          'singleEvents': true,
          'maxResults': 10,
          'orderBy': 'startTime'
        });

        request.execute(function(resp) {
          var events = resp.items;
          appendPre('Upcoming events:');

          if (events.length > 0) {
            for (i = 0; i < events.length; i++) {
              var event = events[i];
              var when = event.start.dateTime;
              if (!when) {
                when = event.start.date;
              }
              appendPre(event.summary + ' (' + when + ')')
            }
          } else {
            appendPre('No upcoming events found.');
          }

        });
      }

      /**
       * Append a pre element to the body containing the given message
       * as its text node.
       *
       * @param {string} message Text to be placed in pre element.
       */
      function appendPre(message) {
        var pre = document.getElementById('output');
        var textContent = document.createTextNode(message + '\n');
        pre.appendChild(textContent);
      }



